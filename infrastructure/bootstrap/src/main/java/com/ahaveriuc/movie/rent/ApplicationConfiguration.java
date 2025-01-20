package com.ahaveriuc.movie.rent;

import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.movie.ReadMoviePort;
import com.ahaveriuc.service.rent.CalculateMovieRentPriceService;
import com.ahaveriuc.service.rent.CalculateRentReturnPriceService;
import com.ahaveriuc.service.rent.calculation.strategy.base.RentCalculationStrategy;
import com.ahaveriuc.service.rent.calculation.strategy.penalty.PenaltyCalculationStrategy;
import com.ahaveriuc.usecase.DomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = "com.ahaveriuc", includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})})
public class ApplicationConfiguration {
    @Bean
    public CalculateMovieRentPriceService calculateMovieRentPriceService(ReadMoviePort readMoviePort, List<RentCalculationStrategy> rentCalculationStrategies) {
        Map<Movie.Type, RentCalculationStrategy> strategyMap = rentCalculationStrategies.stream().collect(Collectors.toMap(RentCalculationStrategy::getType, Function.identity()));
        return new CalculateMovieRentPriceService(readMoviePort, strategyMap);
    }

    @Bean
    public CalculateRentReturnPriceService calculateRentReturnPriceService(ReadMoviePort readMoviePort, List<RentCalculationStrategy> rentCalculationStrategies, List<PenaltyCalculationStrategy> penaltyCalculationStrategies) {
        Map<Movie.Type, RentCalculationStrategy> rentCalculationStrategyMap = rentCalculationStrategies.stream().collect(Collectors.toMap(RentCalculationStrategy::getType, Function.identity()));
        Map<Movie.Type, PenaltyCalculationStrategy> penaltyCalculationStrategyMap = penaltyCalculationStrategies.stream().collect(Collectors.toMap(PenaltyCalculationStrategy::getType, Function.identity()));

        return new CalculateRentReturnPriceService(readMoviePort, rentCalculationStrategyMap, penaltyCalculationStrategyMap);
    }
}
