package com.ahaveriuc.model.rent.returnation;


import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.rent.RentActualEndDate;
import com.ahaveriuc.model.rent.RentExpectedEndDate;
import com.ahaveriuc.model.rent.RentStartDate;

public record RentReturnDetail(MovieId movieId,
                               RentStartDate rentStartDate,
                               RentExpectedEndDate rentExpectedEndDate,
                               RentActualEndDate rentActualEndDate) {
}
