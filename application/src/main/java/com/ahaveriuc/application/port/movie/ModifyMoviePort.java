package com.ahaveriuc.application.port.movie;


import com.ahaveriuc.domain.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.domain.model.movie.MovieId;

public interface ModifyMoviePort {

    MovieId saveMovie(AddMovieToCatalogContext addMovieToCatalogContext);

}
