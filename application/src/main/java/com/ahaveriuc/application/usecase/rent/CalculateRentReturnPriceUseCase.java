package com.ahaveriuc.application.usecase.rent;


import com.ahaveriuc.domain.model.rent.returnation.RentReturnContext;
import com.ahaveriuc.domain.model.rent.returnation.RentReturnPricingResult;

public interface CalculateRentReturnPriceUseCase {
    RentReturnPricingResult execute(RentReturnContext rentReturnDetails);
}
