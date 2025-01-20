package com.ahaveriuc.model.movie;


import com.ahaveriuc.guards.NotNullGuard;

import java.util.UUID;

public record MovieId(UUID id) {

    public MovieId {
        NotNullGuard.notNull(id, "movieId");
    }

}
