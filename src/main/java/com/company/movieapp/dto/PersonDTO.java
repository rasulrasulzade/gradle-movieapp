package com.company.movieapp.dto;

import com.company.movieapp.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToOne
    private Country country;
}
