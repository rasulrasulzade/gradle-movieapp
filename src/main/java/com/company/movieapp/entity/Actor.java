package com.company.movieapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "actor")
@Data
public class Actor {
    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToOne
    private Country country;

    @ManyToMany(mappedBy = "actors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Movie> movies;
}
