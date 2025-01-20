package com.ahaveriuc.service.rent.calculation.strategy.penalty;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.payment.Currency;
import com.ahaveriuc.model.payment.Money;
import com.ahaveriuc.usecase.DomainService;

import java.math.BigDecimal;

@DomainService
public class OldFilmPenaltyCalculationStrategy implements PenaltyCalculationStrategy {

    @Override
    public Money calculatePenalty(Long rentExtraDays) {
        return new Money(BigDecimal.valueOf(30 * rentExtraDays), Currency.SEK);
    }

    @Override
    public Movie.Type getType() {
        return Movie.Type.OLD_FILM;
    }
}
