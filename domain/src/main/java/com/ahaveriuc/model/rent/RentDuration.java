package com.ahaveriuc.model.rent;


import com.ahaveriuc.exception.DomainValidationException;
import com.ahaveriuc.guards.NotNullGuard;

public record RentDuration(Long days) {
    public RentDuration {
        NotNullGuard.notNull(days, "rentDurationDays");

        if (days <= 0) {
            throw new DomainValidationException("Rent duration must have positive value");
        }
    }
}
