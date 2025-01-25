package com.ahaveriuc.domain.model.movie;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Movie {

    private MovieId id;
    private MovieName name;
    private Type type;

    public enum Type {
        NEW_RELEASE,
        REGULAR_FILM,
        OLD_FILM
    }
}
