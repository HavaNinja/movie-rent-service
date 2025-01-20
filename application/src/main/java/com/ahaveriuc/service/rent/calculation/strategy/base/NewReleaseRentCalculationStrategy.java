package com.ahaveriuc.service.rent.calculation.strategy.base;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.payment.Currency;
import com.ahaveriuc.model.payment.Money;
import com.ahaveriuc.usecase.DomainService;

import java.math.BigDecimal;

@DomainService
public class NewReleaseRentCalculationStrategy implements RentCalculationStrategy {

    @Override
    public Money calculatePrice(Long rentalDays) {
        return new Money(BigDecimal.valueOf(rentalDays * 40), Currency.SEK);
    }

    @Override
    public Movie.Type getType() {
        return Movie.Type.NEW_RELEASE;
    }
}
