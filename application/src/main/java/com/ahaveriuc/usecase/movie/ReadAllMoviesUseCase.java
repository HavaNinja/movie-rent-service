package com.ahaveriuc.usecase.movie;


import com.ahaveriuc.model.movie.Movie;

import java.util.List;

public interface ReadAllMoviesUseCase {
    List<Movie> execute();
}
