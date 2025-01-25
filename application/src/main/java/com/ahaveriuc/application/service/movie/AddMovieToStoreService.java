package com.ahaveriuc.application.service.movie;

import com.ahaveriuc.application.port.movie.ModifyMoviePort;
import com.ahaveriuc.application.usecase.DomainService;
import com.ahaveriuc.application.usecase.movie.AddMovieToStoreUseCase;
import com.ahaveriuc.domain.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.domain.model.movie.MovieId;
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
