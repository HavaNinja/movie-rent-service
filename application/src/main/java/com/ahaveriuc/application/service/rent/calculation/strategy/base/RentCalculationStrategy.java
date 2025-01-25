package com.ahaveriuc.application.service.rent.calculation.strategy.base;


import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.payment.Money;

public interface RentCalculationStrategy {

    Money calculatePrice(Long rentalDays);

    Movie.Type getType();

}
