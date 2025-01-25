package com.ahaveriuc.application.usecase.rent;


import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationContext;
import com.ahaveriuc.domain.model.rent.calculation.RentPricingCalculationResult;

public interface CalculateMovieRentPriceUseCase {
    RentPricingCalculationResult execute(RentPricingCalculationContext rentalCalculationDetails);
}
