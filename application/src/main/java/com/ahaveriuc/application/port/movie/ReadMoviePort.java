package com.ahaveriuc.application.port.movie;


import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.movie.MovieId;

import java.util.List;

public interface ReadMoviePort {
    List<Movie> readMovieBy(List<MovieId> movieId);

    List<Movie> readAllMovies();
}
