package com.ahaveriuc.service.rent.calculation.strategy.base;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.payment.Currency;
import com.ahaveriuc.model.payment.Money;
import com.ahaveriuc.usecase.DomainService;

import java.math.BigDecimal;

@DomainService
public class RegularFilmRentCalculationStrategy implements RentCalculationStrategy {

    @Override
    public Money calculatePrice(Long rentalDays) {
        if (rentalDays <= 3) {
            return new Money(BigDecimal.valueOf(30), Currency.SEK);
        } else {
            return new Money(BigDecimal.valueOf(30 + ((rentalDays - 3) * 30)), Currency.SEK);
        }
    }

    @Override
    public Movie.Type getType() {
        return Movie.Type.REGULAR_FILM;
    }
}
