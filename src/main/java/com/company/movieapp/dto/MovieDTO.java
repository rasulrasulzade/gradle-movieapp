package com.company.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private UUID id;

    private String name;

    private Integer year;

    private Float imdb;

    PersonDTO director;

    List<PersonDTO>  actors;
}
