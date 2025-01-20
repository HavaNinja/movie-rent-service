package com.ahaveriuc.model.rent;


import com.ahaveriuc.guards.NotNullGuard;

import java.time.LocalDate;

public record RentExpectedEndDate(LocalDate endDate) {
    public RentExpectedEndDate {
        NotNullGuard.notNull(endDate, "RentEndDate");
    }
}
