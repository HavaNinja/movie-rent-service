package com.ahaveriuc.domain.model.rent;


import com.ahaveriuc.domain.guards.NotNullGuard;

import java.time.LocalDate;

public record RentActualEndDate(LocalDate endDate) {
    public RentActualEndDate {
        NotNullGuard.notNull(endDate, "endDate");
    }
}
