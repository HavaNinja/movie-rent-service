package com.ahaveriuc.model.rent;


import com.ahaveriuc.guards.NotNullGuard;

import java.time.LocalDate;

public record RentStartDate(LocalDate startDate) {
    public RentStartDate {
        NotNullGuard.notNull(startDate, "RentStartDate");
    }
}
