package com.ahaveriuc.bootstrap;

import com.ahaveriuc.application.port.movie.ReadMoviePort;
import com.ahaveriuc.application.service.rent.CalculateMovieRentPriceService;
import com.ahaveriuc.application.service.rent.CalculateRentReturnPriceService;
import com.ahaveriuc.application.service.rent.calculation.strategy.base.RentCalculationStrategy;
import com.ahaveriuc.application.service.rent.calculation.strategy.penalty.PenaltyCalculationStrategy;
import com.ahaveriuc.application.usecase.DomainService;
import com.ahaveriuc.domain.model.movie.Movie;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EntityScan(basePackages = "com.ahaveriuc")
@EnableJpaRepositories(basePackages = "com.ahaveriuc")
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
