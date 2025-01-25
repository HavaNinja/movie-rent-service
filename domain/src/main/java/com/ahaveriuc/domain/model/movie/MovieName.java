package com.ahaveriuc.domain.model.movie;


import com.ahaveriuc.domain.guards.NotNullGuard;

public record MovieName(String name) {
    public MovieName {
        NotNullGuard.notNull(name, "name");
    }
}
