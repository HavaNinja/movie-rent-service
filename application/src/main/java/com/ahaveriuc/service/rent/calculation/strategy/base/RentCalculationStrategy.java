package com.ahaveriuc.service.rent.calculation.strategy.base;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.payment.Money;

public interface RentCalculationStrategy {

    Money calculatePrice(Long rentalDays);

    Movie.Type getType();

}
