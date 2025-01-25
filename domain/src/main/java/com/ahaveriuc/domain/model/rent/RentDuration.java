package com.ahaveriuc.domain.model.rent;


import com.ahaveriuc.domain.exception.DomainValidationException;
import com.ahaveriuc.domain.guards.NotNullGuard;

public record RentDuration(Long days) {
    public RentDuration {
        NotNullGuard.notNull(days, "rentDurationDays");

        if (days <= 0) {
            throw new DomainValidationException("Rent duration must have positive value");
        }
    }
}
