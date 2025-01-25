package com.ahaveriuc.domain.model.rent.returnation;


import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.rent.RentBasePrice;
import com.ahaveriuc.domain.model.rent.RentDuration;
import com.ahaveriuc.domain.model.rent.RentPenalty;
import com.ahaveriuc.domain.model.rent.RentTotalPrice;

public record RentReturnPricingDetail(
        MovieId movieId,
        RentDuration rentDuration,
        RentBasePrice basePrice,
        RentPenalty penalty,
        RentTotalPrice totalPrice) {
}
