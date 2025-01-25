package com.ahaveriuc.domain.model.rent.calculation;


import com.ahaveriuc.domain.guards.NotNullGuard;

import java.util.List;


public record RentPricingCalculationContext(List<RentCalculationDetail> rentCalculationDetails) {

    public RentPricingCalculationContext {
        NotNullGuard.notNull(rentCalculationDetails, "rentCalculationDetails");
    }
}
