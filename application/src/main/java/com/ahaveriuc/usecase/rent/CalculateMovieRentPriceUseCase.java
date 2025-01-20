package com.ahaveriuc.usecase.rent;


import com.ahaveriuc.model.rent.calculation.RentPricingCalculationContext;
import com.ahaveriuc.model.rent.calculation.RentPricingCalculationResult;

public interface CalculateMovieRentPriceUseCase {
    RentPricingCalculationResult execute(RentPricingCalculationContext rentalCalculationDetails);
}
