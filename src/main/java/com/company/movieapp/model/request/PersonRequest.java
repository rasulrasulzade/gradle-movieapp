package com.company.movieapp.model.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PersonRequest {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private UUID countryId;

}
