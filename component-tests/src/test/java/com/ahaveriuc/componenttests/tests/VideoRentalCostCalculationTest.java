package com.ahaveriuc.componenttests.tests;

import com.ahaveriuc.componenttests.ComponentTest;
import com.ahaveriuc.web.movie.dto.AddMovieToCatalogRequest;
import com.ahaveriuc.web.rent.dto.calculation.MovieRentCalculationRequest;
import com.ahaveriuc.web.rent.dto.calculation.MovieRentCalculationResponse;
import com.ahaveriuc.web.rent.dto.returnment.MovieRentReturnRequest;
import com.ahaveriuc.web.rent.dto.returnment.MovieRentReturnResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
class VideoRentalCostCalculationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${embedded.service.host}")
    String host;
    @Value("${embedded.service.port}")
    int port;

    @Test
    void videoRentCalculation() {

        // Given
        UUID matrixId = addMovieToCatalogue("Matrix11", "NEW_RELEASE");
        UUID spiderManId = addMovieToCatalogue("Spider Man", "REGULAR_FILM");
        UUID spiderMan2Id = addMovieToCatalogue("Spider Man 2", "REGULAR_FILM");
        UUID outOfAfricaId = addMovieToCatalogue("Out of Africa", "OLD_FILM");

        MovieRentCalculationRequest requestBody = new MovieRentCalculationRequest();
        requestBody.setRentCalculationRequestDetails(List.of(
                createRentCalculationRequestDetail(matrixId, 1L),
                createRentCalculationRequestDetail(spiderManId, 5L),
                createRentCalculationRequestDetail(spiderMan2Id, 2L),
                createRentCalculationRequestDetail(outOfAfricaId, 7L)
        ));

        // When
        MovieRentCalculationResponse movieRentCalculationResponse = calculateRentPrice(requestBody);

        // Then
        assertThat(movieRentCalculationResponse.getTotalAmount().getPriceAmount().longValue()).isEqualTo(250L);
        assertThat(movieRentCalculationResponse.getTotalAmount().getCurrency()).isEqualTo("SEK");

        Map<UUID, List<MovieRentCalculationResponse.MovieRentCalculationDetail>> groupedRentCalculationDetails = movieRentCalculationResponse.getRentCalculationDetails()
                .stream()
                .collect(Collectors.groupingBy(MovieRentCalculationResponse.MovieRentCalculationDetail::getMovieId));

        MovieRentCalculationResponse.MovieRentCalculationDetail matrixRentCalculation = groupedRentCalculationDetails.get(matrixId).get(0);
        assertThat(matrixRentCalculation.getRentPrice().getPriceAmount()).isEqualTo(BigDecimal.valueOf(40L));
        assertThat(matrixRentCalculation.getRentPrice().getCurrency()).isEqualTo("SEK");
        assertThat(matrixRentCalculation.getRentDuration()).isEqualTo(1L);

        MovieRentCalculationResponse.MovieRentCalculationDetail spiderManRentCalculation = groupedRentCalculationDetails.get(spiderManId).get(0);
        assertThat(spiderManRentCalculation.getRentPrice().getPriceAmount()).isEqualTo(BigDecimal.valueOf(90));
        assertThat(spiderManRentCalculation.getRentPrice().getCurrency()).isEqualTo("SEK");
        assertThat(spiderManRentCalculation.getRentDuration()).isEqualTo(5L);

        MovieRentCalculationResponse.MovieRentCalculationDetail spiderMan2RentCalculation = groupedRentCalculationDetails.get(spiderMan2Id).get(0);
        assertThat(spiderMan2RentCalculation.getRentPrice().getPriceAmount()).isEqualTo(BigDecimal.valueOf(30));
        assertThat(spiderMan2RentCalculation.getRentPrice().getCurrency()).isEqualTo("SEK");
        assertThat(spiderMan2RentCalculation.getRentDuration()).isEqualTo(2L);

        MovieRentCalculationResponse.MovieRentCalculationDetail outOfAfricaRentCalculationResponse = groupedRentCalculationDetails.get(outOfAfricaId).get(0);
        assertThat(outOfAfricaRentCalculationResponse.getRentPrice().getPriceAmount()).isEqualTo(BigDecimal.valueOf(90));
        assertThat(outOfAfricaRentCalculationResponse.getRentPrice().getCurrency()).isEqualTo("SEK");
        assertThat(outOfAfricaRentCalculationResponse.getRentDuration()).isEqualTo(7);
    }

    @Test
    void videoRentReturnWithPenalty() {

        // Given
        UUID matrixId = addMovieToCatalogue("Matrix11", "NEW_RELEASE");
        UUID spiderManId = addMovieToCatalogue("Spider Man", "REGULAR_FILM");

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        MovieRentReturnRequest requestBody = new MovieRentReturnRequest();
        requestBody.setRentReturnRequestDetails(List.of(
                createReturnRequestDetail(matrixId, today, tomorrow, dayAfterTomorrow),
                createReturnRequestDetail(spiderManId, today, tomorrow, tomorrow)
        ));

        // When
        MovieRentReturnResponse movieRentCalculationResponse = returnRent(requestBody);

        // Then
        assertThat(movieRentCalculationResponse.getTotalAmount().getTotalPrice().longValue()).isEqualTo(110L);
        assertThat(movieRentCalculationResponse.getTotalAmount().getCurrency()).isEqualTo("SEK");

        Map<UUID, List<MovieRentReturnResponse.MovieRentReturnResponseDetail>> groupedResponseDetails = movieRentCalculationResponse.getRentReturnDetails()
                .stream()
                .collect(Collectors.groupingBy(MovieRentReturnResponse.MovieRentReturnResponseDetail::getMovieId));

        MovieRentReturnResponse.MovieRentReturnResponseDetail matrixReturnDetail = groupedResponseDetails.get(matrixId).getFirst();
        assertThat(matrixReturnDetail.getRentPrice().getTotalPrice()).isEqualTo(BigDecimal.valueOf(80L));
        assertThat(matrixReturnDetail.getRentPrice().getPenalty()).isEqualTo(BigDecimal.valueOf(40L));
        assertThat(matrixReturnDetail.getRentPrice().getBasePrice()).isEqualTo(BigDecimal.valueOf(40L));
        assertThat(matrixReturnDetail.getRentPrice().getCurrency()).isEqualTo("SEK");
        assertThat(matrixReturnDetail.getRentDuration()).isEqualTo(2);

        MovieRentReturnResponse.MovieRentReturnResponseDetail spiderManReturnDetail = groupedResponseDetails.get(spiderManId).getFirst();
        assertThat(spiderManReturnDetail.getRentPrice().getTotalPrice()).isEqualTo(BigDecimal.valueOf(30L));
        assertThat(spiderManReturnDetail.getRentPrice().getPenalty()).isEqualTo(BigDecimal.valueOf(0L));
        assertThat(spiderManReturnDetail.getRentPrice().getBasePrice()).isEqualTo(BigDecimal.valueOf(30L));
        assertThat(spiderManReturnDetail.getRentPrice().getCurrency()).isEqualTo("SEK");
        assertThat(spiderManReturnDetail.getRentDuration()).isEqualTo(1);
    }

    private UUID addMovieToCatalogue(String name, String type) {
        AddMovieToCatalogRequest addMovieRequest = new AddMovieToCatalogRequest();
        addMovieRequest.setName(name);
        addMovieRequest.setType(type);

        HttpEntity<Object> httpEntity = new HttpEntity<>(addMovieRequest);
        ResponseEntity<Void> httpResponse = restTemplate.postForEntity("http://%s:%s/api/catalog/movie".formatted(host, port), httpEntity, Void.class);

        assertThat(httpResponse.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());

        return UUID.fromString(httpResponse.getHeaders().get("location").getFirst());
    }

    private MovieRentCalculationResponse calculateRentPrice(MovieRentCalculationRequest requestBody) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody);
        ResponseEntity<MovieRentCalculationResponse> httpResponse = restTemplate.postForEntity("http://%s:%s/api/rent/calculation".formatted(host, port), httpEntity, MovieRentCalculationResponse.class);

        assertThat(httpResponse.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

        return httpResponse.getBody();
    }

    private MovieRentReturnResponse returnRent(MovieRentReturnRequest requestBody) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody);
        ResponseEntity<MovieRentReturnResponse> httpResponse = restTemplate.postForEntity("http://%s:%s/api/rent/return".formatted(host, port), httpEntity, MovieRentReturnResponse.class);

        assertThat(httpResponse.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

        return httpResponse.getBody();
    }

    private MovieRentCalculationRequest.RentCalculationRequestDetail createRentCalculationRequestDetail(UUID movieId, Long numberOfRentDays) {
        MovieRentCalculationRequest.RentCalculationRequestDetail detail = new MovieRentCalculationRequest.RentCalculationRequestDetail();
        detail.setMovieId(movieId);
        detail.setRentStartDate(LocalDate.now());
        detail.setRentEndDate(LocalDate.now().plusDays(numberOfRentDays));

        return detail;
    }

    private MovieRentReturnRequest.RentReturnRequestDetail createReturnRequestDetail(UUID movieId, LocalDate startDate, LocalDate expectedEndDate, LocalDate actualEndDate) {
        MovieRentReturnRequest.RentReturnRequestDetail detail = new MovieRentReturnRequest.RentReturnRequestDetail();
        detail.setMovieId(movieId);
        detail.setRentStartDate(startDate);
        detail.setExpectedRentEndDate(expectedEndDate);
        detail.setActualRentEndDate(actualEndDate);

        return detail;
    }
}