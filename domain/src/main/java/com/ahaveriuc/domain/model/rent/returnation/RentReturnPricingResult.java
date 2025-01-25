package com.ahaveriuc.domain.model.rent.returnation;


import com.ahaveriuc.domain.model.rent.RentTotalPrice;

import java.util.List;

public record RentReturnPricingResult(List<RentReturnPricingDetail> returnPricingDetail, RentTotalPrice totalPrice) {
}