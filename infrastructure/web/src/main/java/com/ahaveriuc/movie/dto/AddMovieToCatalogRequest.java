package com.ahaveriuc.movie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMovieToCatalogRequest {
    private String name;
    private String type;
}
