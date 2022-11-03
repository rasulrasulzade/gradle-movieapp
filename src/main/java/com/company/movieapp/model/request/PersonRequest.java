package com.company.movieapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private UUID countryId;

}
