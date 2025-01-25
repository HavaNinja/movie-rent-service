package com.ahaveriuc.application.service.rent;


import com.ahaveriuc.application.UnitTest;
import com.ahaveriuc.application.port.movie.ReadMoviePort;
import com.ahaveriuc.application.service.rent.calculation.strategy.base.RentCalculationStrategy;
import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.payment.Currency;
import com.ahaveriuc.domain.model.payment.Money;
import com.ahaveriuc.domain.model.rent.RentExpectedEndDate;
import com.ahaveriuc.domain.model.rent.RentStartDate;
import com.ahaveriuc.domain.model.rent.calculation.RentCalculationDetail;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationContext;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationDetail;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationResult;
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
