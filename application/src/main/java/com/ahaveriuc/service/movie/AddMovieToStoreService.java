package com.ahaveriuc.service.movie;

import com.ahaveriuc.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.movie.ModifyMoviePort;
import com.ahaveriuc.usecase.DomainService;
import com.ahaveriuc.usecase.movie.AddMovieToStoreUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AddMovieToStoreService implements AddMovieToStoreUseCase {

    private final ModifyMoviePort modifyMoviePort;

    @Override
    public MovieId execute(AddMovieToCatalogContext addMovieToCatalogContext) {
        return modifyMoviePort.saveMovie(addMovieToCatalogContext);
    }
}
