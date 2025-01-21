package com.ahaveriuc.rent.dto.calculation;


import com.ahaveriuc.model.rent.calculation.RentPricingCalculationDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRentCalculationResponse {

    private List<MovieRentCalculationDetail> rentCalculationDetails;
    private RentPrice totalAmount;

    @Getter
    @Setter
    public static class MovieRentCalculationDetail {

        private UUID movieId;
        private RentPrice rentPrice;
        private Long rentDuration;

        public static MovieRentCalculationDetail valueOf(RentPricingCalculationDetail source) {

            MovieRentCalculationDetail destination = new MovieRentCalculationDetail();

            destination.setMovieId(source.movieId().id());

            RentPrice price = new RentPrice();
            price.setCurrency(source.totalCost().price().currency().toString());
            price.setPriceAmount(source.totalCost().price().amount());
            destination.setRentPrice(price);

            destination.setRentDuration(source.rentDuration().days());

            return destination;
        }
    }

    @Setter
    @Getter
    public static class RentPrice {

        private BigDecimal priceAmount;
        private String currency;
    }
}
