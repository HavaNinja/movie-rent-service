package com.ahaveriuc.model.rent.calculation;


import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.rent.RentBasePrice;
import com.ahaveriuc.model.rent.RentDuration;

public record RentPricingCalculationDetail(
        MovieId movieId,
        RentBasePrice totalCost,
        RentDuration rentDuration) {
}
