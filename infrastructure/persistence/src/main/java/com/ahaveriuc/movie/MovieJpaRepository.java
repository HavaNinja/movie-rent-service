package com.ahaveriuc.movie;


import com.ahaveriuc.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, UUID> {
    List<MovieEntity> findMoviesByIdIn(List<UUID> movieId);
}
