package com.ahaveriuc.service.rent.calculation.strategy.penalty;

import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.payment.Money;

public interface PenaltyCalculationStrategy {

    Money calculatePenalty(Long rentExtraDays);

    Movie.Type getType();
}
