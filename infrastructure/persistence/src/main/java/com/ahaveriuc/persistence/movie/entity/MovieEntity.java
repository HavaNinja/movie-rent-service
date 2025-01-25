package com.ahaveriuc.persistence.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Movies", schema = "rental")
@Getter
@Setter
public class MovieEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String type;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
