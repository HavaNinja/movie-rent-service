package com.ahaveriuc.service.rent;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.payment.Currency;
import com.ahaveriuc.model.payment.Money;
import com.ahaveriuc.model.rent.RentBasePrice;
import com.ahaveriuc.model.rent.RentDuration;
import com.ahaveriuc.model.rent.RentPenalty;
import com.ahaveriuc.model.rent.RentTotalPrice;
import com.ahaveriuc.model.rent.returnation.RentReturnContext;
import com.ahaveriuc.model.rent.returnation.RentReturnDetail;
import com.ahaveriuc.model.rent.returnation.RentReturnPricingDetail;
import com.ahaveriuc.model.rent.returnation.RentReturnPricingResult;
import com.ahaveriuc.movie.ReadMoviePort;
import com.ahaveriuc.service.rent.calculation.strategy.base.RentCalculationStrategy;
import com.ahaveriuc.service.rent.calculation.strategy.penalty.PenaltyCalculationStrategy;
import com.ahaveriuc.usecase.rent.CalculateRentReturnPriceUseCase;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CalculateRentReturnPriceService implements CalculateRentReturnPriceUseCase {

    private final ReadMoviePort readMoviePort;
    private final Map<Movie.Type, RentCalculationStrategy> rentCalculationStrategies;
    private final Map<Movie.Type, PenaltyCalculationStrategy> penaltyCalculationStrategies;

    @Override
    public RentReturnPricingResult execute(RentReturnContext rentReturnDetails) {
        List<MovieId> movieIds = rentReturnDetails.rentReturnDetails()
                .stream()
                .map(RentReturnDetail::movieId)
                .toList();

        Map<MovieId, List<Movie>> movies = readMoviePort.readMovieBy(movieIds)
                .stream()
                .collect(Collectors.groupingBy(Movie::getId));

        List<RentReturnPricingDetail> rentReturnPricingDetails = rentReturnDetails.rentReturnDetails().stream().map(detail -> {
                    long expectedRentDays = ChronoUnit.DAYS.between(detail.rentStartDate().startDate(), detail.rentExpectedEndDate().endDate());
                    long actualRentDays = ChronoUnit.DAYS.between(detail.rentStartDate().startDate(), detail.rentActualEndDate().endDate());
                    long penaltyDays = actualRentDays - expectedRentDays;

                    Movie.Type movieType = movies.get(detail.movieId()).getFirst().getType();
                    Money basePrice = rentCalculationStrategies.get(movieType).calculatePrice(expectedRentDays);
                    Money penalty = penaltyCalculationStrategies.get(movieType).calculatePenalty(penaltyDays);

                    // Suppose that all prices are in the same currency
                    Money totalPrice = new Money(basePrice.amount().add(penalty.amount()), basePrice.currency());

                    return new RentReturnPricingDetail(detail.movieId(), new RentDuration(actualRentDays), new RentBasePrice(basePrice), new RentPenalty(penalty), new RentTotalPrice(totalPrice));
                }
        ).toList();

        // Suppose that all prices are in the same currency
        BigDecimal totalRentAmount = rentReturnPricingDetails.stream().map(detail -> detail.totalPrice().total().amount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        Currency currency = rentReturnPricingDetails.getFirst().basePrice().price().currency();

        return new RentReturnPricingResult(rentReturnPricingDetails, new RentTotalPrice(new Money(totalRentAmount, currency)));
    }
}
