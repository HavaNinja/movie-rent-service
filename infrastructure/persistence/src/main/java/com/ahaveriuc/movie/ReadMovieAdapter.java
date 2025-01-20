package com.ahaveriuc.movie;


import com.ahaveriuc.model.movie.Movie;
import com.ahaveriuc.model.movie.MovieId;
import com.ahaveriuc.model.movie.MovieName;
import com.ahaveriuc.movie.entity.MovieEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class ReadMovieAdapter implements ReadMoviePort {

    private final MovieJpaRepository movieJpaRepository;

    public ReadMovieAdapter(MovieJpaRepository movieJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public List<Movie> readMovieBy(List<MovieId> movieId) {

        List<UUID> ids = movieId.stream().map(MovieId::id).toList();
        List<MovieEntity> movieEntities = movieJpaRepository.findMoviesByIdIn(ids);

        return movieEntities.stream().map(entity -> {
            Movie movie = new Movie();
            movie.setId(new MovieId(entity.getId()));
            movie.setName(new MovieName(entity.getName()));
            movie.setType(Movie.Type.valueOf(entity.getType()));
            return movie;
        }).toList();
    }

    @Override
    public List<Movie> readAllMovies() {

        return movieJpaRepository.findAll().stream()
                .map(movieEntity -> {
                    Movie movie = new Movie();
                    movie.setType(Movie.Type.valueOf(movieEntity.getType()));
                    movie.setName(new MovieName(movieEntity.getName()));
                    movie.setId(new MovieId(movieEntity.getId()));
                    return movie;
                }).toList();

    }
}
