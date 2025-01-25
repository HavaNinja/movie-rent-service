package com.ahaveriuc.application.service.rent.calculation.strategy.base;


import com.ahaveriuc.application.usecase.DomainService;
import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.payment.Currency;
import com.ahaveriuc.domain.model.payment.Money;

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
