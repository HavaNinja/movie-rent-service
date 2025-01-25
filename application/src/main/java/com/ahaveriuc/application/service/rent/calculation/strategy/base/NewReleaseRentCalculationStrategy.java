package com.ahaveriuc.application.service.rent.calculation.strategy.base;


import com.ahaveriuc.application.usecase.DomainService;
import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.payment.Currency;
import com.ahaveriuc.domain.model.payment.Money;

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
