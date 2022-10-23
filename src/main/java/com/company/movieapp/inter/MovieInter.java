package com.company.movieapp.inter;

import java.util.UUID;

public interface MovieInter {
    UUID getId();

    String getName();

    Integer getYear();

    Float getImdb();

    UUID getDirectorId();

    String getDirectorFullName();
}
