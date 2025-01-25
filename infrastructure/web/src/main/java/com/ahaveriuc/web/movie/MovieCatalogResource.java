package com.ahaveriuc.web.movie;


import com.ahaveriuc.application.usecase.movie.AddMovieToStoreUseCase;
import com.ahaveriuc.application.usecase.movie.ReadAllMoviesUseCase;
import com.ahaveriuc.domain.model.movie.AddMovieToCatalogContext;
import com.ahaveriuc.domain.model.movie.Movie;
import com.ahaveriuc.domain.model.movie.MovieId;
import com.ahaveriuc.domain.model.movie.MovieName;
import com.ahaveriuc.web.movie.dto.AddMovieToCatalogRequest;
import com.ahaveriuc.web.movie.dto.ReadMovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/movie")
@RequiredArgsConstructor
public class MovieCatalogResource {

    private final AddMovieToStoreUseCase addMovieToStoreUseCase;
    private final ReadAllMoviesUseCase readAllMoviesUseCase;

    @PostMapping
    public ResponseEntity<Void> addMovieToCatalog(@RequestBody AddMovieToCatalogRequest requestBody) {

        AddMovieToCatalogContext addMovieToCatalogContext = new AddMovieToCatalogContext(Movie.Type.valueOf(requestBody.getType()), new MovieName(requestBody.getName()));
        MovieId newMovieId = addMovieToStoreUseCase.execute(addMovieToCatalogContext);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("location", newMovieId.id().toString())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ReadMovieResponse>> readAllMovies() {

        List<ReadMovieResponse> responseBody = readAllMoviesUseCase.execute()
                .stream().
                map(movie -> {
                    ReadMovieResponse readMovieResponse = new ReadMovieResponse();
                    readMovieResponse.setId(movie.getId().id().toString());
                    readMovieResponse.setName(movie.getName().name());
                    readMovieResponse.setType(movie.getType().name());
                    return readMovieResponse;
                }).toList();

        return ResponseEntity.ok(responseBody);
    }

}
