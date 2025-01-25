package com.ahaveriuc.domain.model.rent;


import com.ahaveriuc.domain.guards.NotNullGuard;

import java.time.LocalDate;

public record RentStartDate(LocalDate startDate) {
    public RentStartDate {
        NotNullGuard.notNull(startDate, "RentStartDate");
    }
}
