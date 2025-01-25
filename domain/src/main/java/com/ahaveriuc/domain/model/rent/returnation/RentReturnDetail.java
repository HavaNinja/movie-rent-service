package com.ahaveriuc.domain.model.rent.returnation;


import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.rent.RentActualEndDate;
import com.ahaveriuc.domain.model.rent.RentExpectedEndDate;
import com.ahaveriuc.domain.model.rent.RentStartDate;

public record RentReturnDetail(MovieId movieId,
                               RentStartDate rentStartDate,
                               RentExpectedEndDate rentExpectedEndDate,
                               RentActualEndDate rentActualEndDate) {
}
