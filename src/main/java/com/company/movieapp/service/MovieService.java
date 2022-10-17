package com.company.movieapp.service;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.model.request.MovieRequest;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    List<MovieDTO> getMovies();

    MovieDTO save(MovieRequest request);

    MovieDTO getById(UUID id);
}
