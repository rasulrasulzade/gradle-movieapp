package com.company.movieapp.service.impl;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.entity.Movie;
import com.company.movieapp.exception.CustomException;
import com.company.movieapp.inter.MovieInter;
import com.company.movieapp.mapstruct.MovieMapStruct;
import com.company.movieapp.model.request.MovieRequest;
import com.company.movieapp.repository.ActorRepository;
import com.company.movieapp.repository.MovieRepository;
import com.company.movieapp.service.DirectorService;
import com.company.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorService directorService;

    private final MovieMapStruct mapStruct;

    @Override
    public List<MovieInter> getMovies(Optional<String> director, Optional<String> actor) {
        String directorId = director.orElse(null);
        List<String> actorIds = actor.map(s -> Arrays.stream(s.split(","))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return movieRepository.findByDirectorIdAndActorIds(directorId, actorIds);
    }

    @Override
    public MovieDTO save(MovieRequest request) {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setName(request.getName());
        movie.setYear(request.getYear());
        movie.setImdb(request.getImdb());
        movie.setDirector(directorService.getDirectorId(request.getDirectorId()));
        movie.setActors(request.getActorIds()
                .stream()
                .map(id-> actorRepository.findById(id).orElse(null))
                .collect(Collectors.toList()));
        return mapStruct.map((movieRepository.save(movie)));
    }

    @Override
    public MovieDTO getById(UUID id) {
        Optional<Movie> opt = movieRepository.findById(id);
        return opt.map(mapStruct::map).orElseThrow(() -> new CustomException("Movie not found with id: " + id, HttpStatus.NOT_FOUND));
    }

}
