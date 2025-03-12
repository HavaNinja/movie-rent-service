package com.ahaveriuc.persistence.movie;


import com.ahaveriuc.persistence.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface MovieJpaRepository extends JpaRepository<MovieEntity, UUID> {
    List<MovieEntity> findMoviesByIdIn(List<UUID> movieId);
}
