package com.company.movieapp.model.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MovieRequest {

    private String name;

    private Integer year;

    private Float imdb;

    UUID directorId;

    List<UUID> actorIds;
}
