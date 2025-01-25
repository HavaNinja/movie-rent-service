package com.ahaveriuc.domain.model.rent.calculation;


import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.rent.RentBasePrice;
import com.ahaveriuc.domain.model.rent.RentDuration;

public record RentPricingCalculationDetail(
        MovieId movieId,
        RentBasePrice totalCost,
        RentDuration rentDuration) {
}
