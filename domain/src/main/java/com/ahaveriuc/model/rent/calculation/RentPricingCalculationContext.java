package com.ahaveriuc.model.rent.calculation;


import com.ahaveriuc.guards.NotNullGuard;

import java.util.List;


public record RentPricingCalculationContext(List<RentCalculationDetail> rentCalculationDetails) {

    public RentPricingCalculationContext {
        NotNullGuard.notNull(rentCalculationDetails, "rentCalculationDetails");
    }
}
