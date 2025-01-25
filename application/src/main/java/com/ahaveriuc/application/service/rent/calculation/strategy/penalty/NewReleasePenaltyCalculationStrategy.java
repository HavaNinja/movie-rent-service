package com.ahaveriuc.application.service.rent.calculation.strategy.penalty;


import com.ahaveriuc.application.usecase.DomainService;
import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.payment.Currency;
import com.ahaveriuc.domain.model.payment.Money;

import java.math.BigDecimal;

@DomainService
public class NewReleasePenaltyCalculationStrategy implements PenaltyCalculationStrategy {

    @Override
    public Money calculatePenalty(Long rentExtraDays) {
        return new Money(BigDecimal.valueOf(40 * rentExtraDays), Currency.SEK);
    }

    @Override
    public Movie.Type getType() {
        return Movie.Type.NEW_RELEASE;
    }
}
