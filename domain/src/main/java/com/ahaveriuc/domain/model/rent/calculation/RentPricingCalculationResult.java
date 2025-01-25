package com.ahaveriuc.domain.model.rent.calculation;


import com.ahaveriuc.domain.model.payment.Money;

import java.util.List;


public record RentPricingCalculationResult(List<RentPricingCalculationDetail> pricingCalculationDetail,
                                           Money totalPrice) {
}
