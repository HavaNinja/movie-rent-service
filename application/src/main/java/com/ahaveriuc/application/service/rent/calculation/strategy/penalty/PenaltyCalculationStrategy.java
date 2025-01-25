package com.ahaveriuc.application.service.rent.calculation.strategy.penalty;

import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.payment.Money;

public interface PenaltyCalculationStrategy {

    Money calculatePenalty(Long rentExtraDays);

    Movie.Type getType();
}
