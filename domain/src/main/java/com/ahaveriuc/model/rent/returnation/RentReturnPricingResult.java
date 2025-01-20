package com.ahaveriuc.model.rent.returnation;


import com.ahaveriuc.model.rent.RentTotalPrice;

import java.util.List;

public record RentReturnPricingResult(List<RentReturnPricingDetail> returnPricingDetail, RentTotalPrice totalPrice) {
}