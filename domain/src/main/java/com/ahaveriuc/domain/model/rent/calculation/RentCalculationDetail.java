package com.ahaveriuc.domain.model.rent.calculation;


import com.ahaveriuc.domain.exception.DomainValidationException;
import com.ahaveriuc.domain.guards.NotNullGuard;
import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.rent.RentExpectedEndDate;
import com.ahaveriuc.domain.model.rent.RentStartDate;

public record RentCalculationDetail(MovieId movieId,
                                    RentStartDate rentStartDate,
                                    RentExpectedEndDate rentExpectedEndDate) {
    public RentCalculationDetail {
        NotNullGuard.notNull(movieId, "movieId");
        NotNullGuard.notNull(rentStartDate, "rentStartDate");
        NotNullGuard.notNull(rentExpectedEndDate, "rentEndDate");

        if (rentExpectedEndDate.endDate().isBefore(rentStartDate.startDate())) {
            throw new DomainValidationException("Rent end date can not be before start date");
        }
    }
}
