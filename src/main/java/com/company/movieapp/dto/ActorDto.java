package com.company.movieapp.dto;

import com.company.movieapp.entity.Country;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ActorDto {
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Country country;

}
