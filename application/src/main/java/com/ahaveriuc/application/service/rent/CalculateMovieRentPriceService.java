package com.ahaveriuc.application.service.rent;


import com.ahaveriuc.application.port.movie.ReadMoviePort;
import com.ahaveriuc.application.service.rent.calculation.strategy.base.RentCalculationStrategy;
import com.ahaveriuc.application.usecase.rent.CalculateMovieRentPriceUseCase;
import com.ahaveriuc.domain.guards.NotNullGuard;
import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.payment.Currency;
import com.ahaveriuc.domain.model.payment.Money;
import com.ahaveriuc.domain.model.rent.RentBasePrice;
import com.ahaveriuc.domain.model.rent.RentDuration;
import com.ahaveriuc.domain.model.rent.calculation.RentCalculationDetail;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationContext;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationDetail;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationResult;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculateMovieRentPriceService implements CalculateMovieRentPriceUseCase {

    private final ReadMoviePort readMoviePort;
    private final Map<Movie.Type, RentCalculationStrategy> rentCalculationStrategies;

    public CalculateMovieRentPriceService(ReadMoviePort readMoviePort, Map<Movie.Type, RentCalculationStrategy> rentCalculationStrategies) {
        NotNullGuard.notNull(readMoviePort, "readMoviePort");
        NotNullGuard.notNull(rentCalculationStrategies, "rentCalculationStrategies");
        if (rentCalculationStrategies.isEmpty()) {
            throw new IllegalStateException("rentCalculationStrategies is empty");
        }

        this.readMoviePort = readMoviePort;
        this.rentCalculationStrategies = rentCalculationStrategies;
    }

    @Override
    public RentPricingCalculationResult execute(RentPricingCalculationContext rentalDetails) {

        List<MovieId> movieIds = rentalDetails.rentCalculationDetails()
                .stream()
                .map(RentCalculationDetail::movieId)
                .toList();

        Map<MovieId, List<Movie>> movies = readMoviePort.readMovieBy(movieIds)
                .stream()
                .collect(Collectors.groupingBy(Movie::getId));

        List<RentPricingCalculationDetail> rentPricingCalculationDetails = rentalDetails.rentCalculationDetails().stream().map(detail -> {
                    long rentalDays = ChronoUnit.DAYS.between(detail.rentStartDate().startDate(), detail.rentExpectedEndDate().endDate());
                    Movie.Type movieType = movies.get(detail.movieId()).getFirst().getType();
                    Money price = rentCalculationStrategies.get(movieType).calculatePrice(rentalDays);

                    return new RentPricingCalculationDetail(detail.movieId(), new RentBasePrice(price), new RentDuration(rentalDays));
                }
        ).toList();

        // Suppose that all prices are in the same currency
        BigDecimal totalPrice = rentPricingCalculationDetails.stream().map(detail -> detail.totalCost().price().amount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        Currency currency = rentPricingCalculationDetails.getFirst().totalCost().price().currency();

        return new RentPricingCalculationResult(rentPricingCalculationDetails, new Money(totalPrice, currency));
    }
}
