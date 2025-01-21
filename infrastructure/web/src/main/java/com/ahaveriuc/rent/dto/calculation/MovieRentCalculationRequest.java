package com.ahaveriuc.rent.dto.calculation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MovieRentCalculationRequest {

    private List<RentCalculationRequestDetail> rentCalculationRequestDetails;

    @Getter
    @Setter
    public static class RentCalculationRequestDetail {
        private UUID movieId;
        private LocalDate rentStartDate;
        private LocalDate rentEndDate;
    }
}
