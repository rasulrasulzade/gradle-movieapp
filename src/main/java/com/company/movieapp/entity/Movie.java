package com.company.movieapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "movie")
@Data
public class Movie {
    @Id
    private UUID id;

    private String name;

    private Integer year;

    private Float imdb;

    @OneToOne
    Director director;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "movie_actor",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    List<Actor> actors;
}
