package com.ahaveriuc.model.movie;


import com.ahaveriuc.guards.NotNullGuard;

public record MovieName(String name) {
    public MovieName {
        NotNullGuard.notNull(name, "name");
    }
}
