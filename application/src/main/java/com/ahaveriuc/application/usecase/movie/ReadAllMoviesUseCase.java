package com.ahaveriuc.application.usecase.movie;


import com.ahaveriuc.domain.model.movie.Movie;

import java.util.List;

public interface ReadAllMoviesUseCase {
    List<Movie> execute();
}
