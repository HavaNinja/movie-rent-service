package com.ahaveriuc.movie;


import com.ahaveriuc.PersistenceTest;
import com.ahaveriuc.movie.entity.MovieEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;


import static com.ahaveriuc.model.movie.Movie.Type.NEW_RELEASE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@PersistenceTest
class MovieJpaRepositoryTest {

    @Autowired
    MovieJpaRepository target;

    @Test
    void movieSaveAndReadById() {

        // Given
        MovieEntity one = new MovieEntity();
        one.setType(NEW_RELEASE.name());
        String oneName = UUID.randomUUID().toString();
        one.setName(oneName);
        target.save(one);

        MovieEntity two = new MovieEntity();
        two.setType(NEW_RELEASE.name());
        String twoName = UUID.randomUUID().toString();
        two.setName(twoName);
        MovieEntity savedTwo = target.save(two);

        // When
        List<MovieEntity> actualResult = target.findMoviesByIdIn(List.of(savedTwo.getId()));

        // Then
        assertEquals(1, actualResult.size());
        assertEquals(twoName, actualResult.getFirst().getName());
        assertEquals(savedTwo.getId(), actualResult.getFirst().getId());
        assertEquals(NEW_RELEASE.name(), actualResult.getFirst().getType());
    }

}