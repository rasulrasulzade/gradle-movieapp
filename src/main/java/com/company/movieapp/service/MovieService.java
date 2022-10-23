package com.company.movieapp.service;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.inter.MovieInter;
import com.company.movieapp.model.request.MovieRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieService {
    List<MovieInter> getMovies(Optional<String> directorId, Optional<String> actorIds);

    MovieDTO save(MovieRequest request);

    MovieDTO getById(UUID id);
}
