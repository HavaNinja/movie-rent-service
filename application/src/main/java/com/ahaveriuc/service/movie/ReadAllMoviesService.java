package com.ahaveriuc.service.movie;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.movie.ReadMoviePort;
import com.ahaveriuc.usecase.DomainService;
import com.ahaveriuc.usecase.movie.ReadAllMoviesUseCase;
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
