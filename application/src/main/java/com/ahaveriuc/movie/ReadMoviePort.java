package com.ahaveriuc.movie;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.movie.MovieId;

import java.util.List;

public interface ReadMoviePort {
    List<Movie> readMovieBy(List<MovieId> movieId);

    List<Movie> readAllMovies();
}
