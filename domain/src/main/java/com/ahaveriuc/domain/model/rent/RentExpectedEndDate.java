package com.ahaveriuc.domain.model.rent;


import com.ahaveriuc.domain.guards.NotNullGuard;

import java.time.LocalDate;

public record RentExpectedEndDate(LocalDate endDate) {
    public RentExpectedEndDate {
        NotNullGuard.notNull(endDate, "RentEndDate");
    }
}
