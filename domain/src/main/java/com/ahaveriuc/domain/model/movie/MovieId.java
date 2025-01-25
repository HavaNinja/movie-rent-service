package com.ahaveriuc.domain.model.movie;


import com.ahaveriuc.domain.guards.NotNullGuard;

import java.util.UUID;

public record MovieId(UUID id) {

    public MovieId {
        NotNullGuard.notNull(id, "movieId");
    }

}
