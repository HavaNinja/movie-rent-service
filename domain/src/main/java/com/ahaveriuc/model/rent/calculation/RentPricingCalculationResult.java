package com.ahaveriuc.model.rent.calculation;


import com.ahaveriuc.model.payment.Money;

import java.util.List;


public record RentPricingCalculationResult(List<RentPricingCalculationDetail> pricingCalculationDetail,
                                           Money totalPrice) {
}
