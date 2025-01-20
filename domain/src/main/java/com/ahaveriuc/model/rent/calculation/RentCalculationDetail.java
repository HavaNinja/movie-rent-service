package com.ahaveriuc.model.rent.calculation;


import com.ahaveriuc.exception.DomainValidationException;
import com.ahaveriuc.guards.NotNullGuard;
import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.rent.RentExpectedEndDate;
import com.ahaveriuc.model.rent.RentStartDate;

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
