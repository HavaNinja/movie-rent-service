package com.ahaveriuc.usecase.rent;


import com.ahaveriuc.model.rent.returnation.RentReturnContext;
import com.ahaveriuc.model.rent.returnation.RentReturnPricingResult;

public interface CalculateRentReturnPriceUseCase {
    RentReturnPricingResult execute(RentReturnContext rentReturnDetails);
}
