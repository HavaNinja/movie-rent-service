package com.ahaveriuc.rent.dto.returnment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MovieRentReturnRequest {

    private List<RentReturnRequestDetail> rentReturnRequestDetails;

    @Getter
    @Setter
    public static class RentReturnRequestDetail {
        private UUID movieId;
        private LocalDate rentStartDate;
        private LocalDate expectedRentEndDate;
        private LocalDate actualRentEndDate;
    }
}
