package com.ahaveriuc.application.usecase.movie;


import com.ahaveriuc.domain.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.domain.model.movie.MovieId;

public interface AddMovieToStoreUseCase {
    MovieId execute(AddMovieToCatalogContext addMovieToCatalogContext);
}
