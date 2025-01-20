package com.ahaveriuc.model.rent.returnation;


import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.rent.RentBasePrice;
import com.ahaveriuc.model.rent.RentDuration;
import com.ahaveriuc.model.rent.RentPenalty;
import com.ahaveriuc.model.rent.RentTotalPrice;

public record RentReturnPricingDetail(
        MovieId movieId,
        RentDuration rentDuration,
        RentBasePrice basePrice,
        RentPenalty penalty,
        RentTotalPrice totalPrice) {
}
