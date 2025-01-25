package com.ahaveriuc.application.service.movie;


import com.ahaveriuc.application.port.movie.ReadMoviePort;
import com.ahaveriuc.application.usecase.DomainService;
import com.ahaveriuc.application.usecase.movie.ReadAllMoviesUseCase;
import com.ahaveriuc.domain.model.movie.Movie;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@DomainService
public class ReadAllMoviesService implements ReadAllMoviesUseCase {

    private final ReadMoviePort readMoviePort;

    @Override
    public List<Movie> execute() {
        return readMoviePort.readAllMovies();
    }
}
