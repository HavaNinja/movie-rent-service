package com.ahaveriuc.service.rent;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.payment.Currency;
import com.ahaveriuc.model.payment.Money;
import com.ahaveriuc.model.rent.RentExpectedEndDate;
import com.ahaveriuc.model.rent.RentStartDate;
import com.ahaveriuc.model.rent.calculation.RentCalculationDetail;
import com.ahaveriuc.model.rent.calculation.RentPricingCalculationContext;
import com.ahaveriuc.model.rent.calculation.RentPricingCalculationDetail;
import com.ahaveriuc.model.rent.calculation.RentPricingCalculationResult;
import com.ahaveriuc.movie.ReadMoviePort;
import com.ahaveriuc.service.rent.calculation.strategy.base.RentCalculationStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@UnitTest
class CalculateMovieRentPriceServiceTest {

    @InjectMocks
    CalculateMovieRentPriceService target;

    @Mock
    ReadMoviePort readMoviePort;
    @Mock
    Map<Movie.Type, RentCalculationStrategy> rentCalculationStrategies;
    @Mock
    RentCalculationStrategy rentCalculationStrategy;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(readMoviePort, rentCalculationStrategies, rentCalculationStrategy);
    }

    @Test
    void calculateMovieRentPrice() {

        // Given
        MovieId movieId = new MovieId(UUID.randomUUID());
        RentStartDate rentStartDate = new RentStartDate(LocalDate.now());
        long rentDays = 1L;
        RentExpectedEndDate rentExpectedEndDate = new RentExpectedEndDate(LocalDate.now().plusDays(rentDays));
        RentCalculationDetail rentCalculationDetail = new RentCalculationDetail(movieId, rentStartDate, rentExpectedEndDate);

        List<RentCalculationDetail> rentCalculationDetails = new ArrayList<>(List.of(rentCalculationDetail));

        Money rentPrice = new Money(BigDecimal.ONE, Currency.SEK);
        Mockito.when(rentCalculationStrategy.calculatePrice(rentDays)).thenReturn(rentPrice);
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setType(Movie.Type.NEW_RELEASE);
        Mockito.when(readMoviePort.readMovieBy(List.of(movieId))).thenReturn(List.of(movie));
        Mockito.when(rentCalculationStrategies.get(movie.getType())).thenReturn(rentCalculationStrategy);
        RentPricingCalculationContext context = new RentPricingCalculationContext(rentCalculationDetails);

        // When
        RentPricingCalculationResult result = target.execute(context);

        // Then
        List<RentPricingCalculationDetail> resultDetails = result.pricingCalculationDetail();
        assertThat(resultDetails).hasSize(1);
        assertThat(resultDetails.getFirst().movieId()).isEqualTo(movieId);
        assertThat(resultDetails.getFirst().rentDuration().days()).isEqualTo(rentDays);
        assertThat(resultDetails.getFirst().totalCost().price()).isEqualTo(rentPrice);

        Mockito.verify(readMoviePort).readMovieBy(List.of(movieId));
        Mockito.verify(rentCalculationStrategies).get(Movie.Type.NEW_RELEASE);
        Mockito.verify(rentCalculationStrategy).calculatePrice(rentDays);
        Mockito.verify(rentCalculationStrategies).isEmpty();
    }
}
