package com.company.movieapp.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "country")
@Data
public class Country {
    @Id
    private UUID id;

    private String name;

    private String nationality;
}
