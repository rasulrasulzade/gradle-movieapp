package com.company.movieapp.service;

import com.company.movieapp.entity.Director;
import com.company.movieapp.model.request.PersonRequest;

import java.util.List;
import java.util.UUID;

public interface DirectorService {

    Director save(PersonRequest request);

    List<Director> getDirectors();

    Director getDirectorId(UUID id);
}
