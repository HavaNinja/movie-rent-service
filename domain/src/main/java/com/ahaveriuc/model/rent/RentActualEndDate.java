package com.ahaveriuc.model.rent;


import com.ahaveriuc.guards.NotNullGuard;

import java.time.LocalDate;

public record RentActualEndDate(LocalDate endDate) {
    public RentActualEndDate {
        NotNullGuard.notNull(endDate, "endDate");
    }
}
