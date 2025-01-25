package com.ahaveriuc.web.rent.dto.returnment;


import com.ahaveriuc.domain.model.rent.returnation.RentReturnPricingDetail;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MovieRentReturnResponse {

    private List<MovieRentReturnResponseDetail> rentReturnDetails;
    private TotalRentPrice totalAmount;

    @Getter
    @Setter
    public static class MovieRentReturnResponseDetail {

        private UUID movieId;
        private RentPrice rentPrice;
        private Long rentDuration;

        public static MovieRentReturnResponseDetail valueOf(RentReturnPricingDetail source) {

            MovieRentReturnResponseDetail destination = new MovieRentReturnResponseDetail();

            destination.setMovieId(source.movieId().id());

            RentPrice price = new RentPrice();
            price.setCurrency(source.basePrice().price().currency().name());
            price.setBasePrice(source.basePrice().price().amount());
            price.setPenalty(source.penalty().penalty().amount());
            price.setTotalPrice(source.totalPrice().total().amount());
            destination.setRentPrice(price);

            destination.setRentDuration(source.rentDuration().days());

            return destination;
        }
    }

    @Setter
    @Getter
    public static class RentPrice {
        private BigDecimal basePrice;
        private BigDecimal totalPrice;
        private BigDecimal penalty;
        private String currency;
    }

    @Setter
    @Getter
    public static class TotalRentPrice {
        private BigDecimal totalPrice;
        private String currency;
    }
}
