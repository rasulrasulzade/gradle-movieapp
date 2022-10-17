package com.company.movieapp.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MovieDTO {
    private UUID id;

    private String name;

    private Integer year;

    private Float imdb;

    PersonDTO director;

    List<PersonDTO>  actors;
}
