package com.ahaveriuc.usecase.movie;


import com.ahaveriuc.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.model.movie.MovieId;

public interface AddMovieToStoreUseCase {
    MovieId execute(AddMovieToCatalogContext addMovieToCatalogContext);
}
