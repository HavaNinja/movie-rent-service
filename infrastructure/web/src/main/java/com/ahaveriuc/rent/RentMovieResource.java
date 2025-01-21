package com.ahaveriuc.rent;


import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.rent.RentActualEndDate;
import com.ahaveriuc.model.rent.RentExpectedEndDate;
import com.ahaveriuc.model.rent.RentStartDate;
import com.ahaveriuc.model.rent.calculation.RentCalculationDetail;
import com.ahaveriuc.model.rent.calculation.RentPricingCalculationContext;
import com.ahaveriuc.model.rent.calculation.RentPricingCalculationResult;
import com.ahaveriuc.model.rent.returnation.RentReturnContext;
import com.ahaveriuc.model.rent.returnation.RentReturnDetail;
import com.ahaveriuc.model.rent.returnation.RentReturnPricingResult;
import com.ahaveriuc.rent.dto.calculation.MovieRentCalculationRequest;
import com.ahaveriuc.rent.dto.calculation.MovieRentCalculationResponse;
import com.ahaveriuc.rent.dto.returnment.MovieRentReturnRequest;
import com.ahaveriuc.rent.dto.returnment.MovieRentReturnResponse;
import com.ahaveriuc.usecase.rent.CalculateMovieRentPriceUseCase;
import com.ahaveriuc.usecase.rent.CalculateRentReturnPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
@RequiredArgsConstructor
public class RentMovieResource {

    private final CalculateMovieRentPriceUseCase calculateMovieRentPriceUseCase;
    private final CalculateRentReturnPriceUseCase calculateRentReturnPriceUseCase;

    @PostMapping("/calculation")
    public ResponseEntity<MovieRentCalculationResponse> calculateRentPrice(@RequestBody MovieRentCalculationRequest requestBody) {
        List<RentCalculationDetail> rentCalculationDetails = requestBody.getRentCalculationRequestDetails()
                .stream()
                .map(requestDetail -> new RentCalculationDetail(new MovieId(requestDetail.getMovieId()), new RentStartDate(requestDetail.getRentStartDate()), new RentExpectedEndDate(requestDetail.getRentEndDate())))
                .toList();
        RentPricingCalculationContext rentPricingCalculationContext = new RentPricingCalculationContext(rentCalculationDetails);
        RentPricingCalculationResult rentPricingCalculationResult = calculateMovieRentPriceUseCase.execute(rentPricingCalculationContext);

        MovieRentCalculationResponse responseBody = mapToMovieRentCalculationResponse(rentPricingCalculationResult);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/return")
    public ResponseEntity<MovieRentReturnResponse> returnMovies(@RequestBody MovieRentReturnRequest requestBody) {
        List<RentReturnDetail> rentCalculationDetails = requestBody.getRentReturnRequestDetails()
                .stream()
                .map(requestDetail -> new RentReturnDetail(new MovieId(requestDetail.getMovieId()),
                        new RentStartDate(requestDetail.getRentStartDate()),
                        new RentExpectedEndDate(requestDetail.getExpectedRentEndDate()),
                        new RentActualEndDate(requestDetail.getActualRentEndDate())))
                .toList();

        RentReturnContext rentPricingCalculationContext = new RentReturnContext(rentCalculationDetails);
        RentReturnPricingResult rentReturnPricingResult = calculateRentReturnPriceUseCase.execute(rentPricingCalculationContext);

        MovieRentReturnResponse responseBody = mapToMovieRentReturnPricingResponse(rentReturnPricingResult);

        return ResponseEntity.ok(responseBody);
    }

    private MovieRentReturnResponse mapToMovieRentReturnPricingResponse(RentReturnPricingResult rentReturnPricingResult) {
        List<MovieRentReturnResponse.MovieRentReturnResponseDetail> rentReturnResponseDetail = rentReturnPricingResult.returnPricingDetail()
                .stream()
                .map(MovieRentReturnResponse.MovieRentReturnResponseDetail::valueOf)
                .toList();

        MovieRentReturnResponse.TotalRentPrice totalRentPrice = new MovieRentReturnResponse.TotalRentPrice();
        totalRentPrice.setCurrency(rentReturnPricingResult.totalPrice().total().currency().name());
        totalRentPrice.setTotalPrice(rentReturnPricingResult.totalPrice().total().amount());

        MovieRentReturnResponse responseBody = new MovieRentReturnResponse();
        responseBody.setRentReturnDetails(rentReturnResponseDetail);
        responseBody.setTotalAmount(totalRentPrice);

        return responseBody;
    }

    private static MovieRentCalculationResponse mapToMovieRentCalculationResponse(RentPricingCalculationResult rentPricingCalculationResult) {
        List<MovieRentCalculationResponse.MovieRentCalculationDetail> rentCalculationResponseDetail = rentPricingCalculationResult.pricingCalculationDetail()
                .stream()
                .map(MovieRentCalculationResponse.MovieRentCalculationDetail::valueOf)
                .toList();

        MovieRentCalculationResponse.RentPrice totalResponseRentPrice = new MovieRentCalculationResponse.RentPrice();
        totalResponseRentPrice.setCurrency(rentPricingCalculationResult.totalPrice().currency().name());
        totalResponseRentPrice.setPriceAmount(rentPricingCalculationResult.totalPrice().amount());
        MovieRentCalculationResponse responseBody = new MovieRentCalculationResponse();
        responseBody.setRentCalculationDetails(rentCalculationResponseDetail);
        responseBody.setTotalAmount(totalResponseRentPrice);
        return responseBody;
    }
}
