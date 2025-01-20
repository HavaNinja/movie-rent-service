package com.ahaveriuc.movie;


import com.ahaveriuc.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.model.movie.MovieId;

public interface ModifyMoviePort {

    MovieId saveMovie(AddMovieToCatalogContext addMovieToCatalogContext);

}
