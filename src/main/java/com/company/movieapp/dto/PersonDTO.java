package com.company.movieapp.dto;

import com.company.movieapp.entity.Country;
import lombok.Data;

import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PersonDTO {
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToOne
    private Country country;
}
