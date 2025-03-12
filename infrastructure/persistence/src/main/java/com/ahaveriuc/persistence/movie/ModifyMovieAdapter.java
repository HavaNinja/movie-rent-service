package com.ahaveriuc.persistence.movie;


import com.ahaveriuc.application.port.movie.ModifyMoviePort;
import com.ahaveriuc.domain.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.persistence.movie.entity.MovieEntity;
import org.springframework.stereotype.Component;

@Component
class ModifyMovieAdapter implements ModifyMoviePort {

    private final MovieJpaRepository movieJpaRepository;

    public ModifyMovieAdapter(MovieJpaRepository movieJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public MovieId saveMovie(AddMovieToCatalogContext addMovieToCatalogContext) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(addMovieToCatalogContext.name().name());
        movieEntity.setType(addMovieToCatalogContext.type().name());

        MovieEntity savedMovie = movieJpaRepository.save(movieEntity);
        return new MovieId(savedMovie.getId());
    }
}
