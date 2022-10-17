package com.company.movieapp.service.impl;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.dto.PersonDTO;
import com.company.movieapp.entity.Actor;
import com.company.movieapp.entity.Director;
import com.company.movieapp.entity.Movie;
import com.company.movieapp.model.request.MovieRequest;
import com.company.movieapp.repository.MovieRepository;
import com.company.movieapp.service.ActorService;
import com.company.movieapp.service.DirectorService;
import com.company.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ActorService actorService;
    private final DirectorService directorService;

    @Override
    public List<MovieDTO> getMovies() {
        return movieRepository.findAll().stream().map(e -> {
            MovieDTO dto = new MovieDTO();
            dto.setId(e.getId());
            dto.setName(e.getName());
            dto.setYear(e.getYear());
            dto.setImdb(e.getImdb());
            dto.setDirector(map(e.getDirector()));
            dto.setActors(map(e.getActors()));
            return dto;
        }).collect(Collectors.toList());
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
                .map(actorService::getActorById)
                .collect(Collectors.toList()));
        return map(movieRepository.save(movie));
    }

    @Override
    public MovieDTO getById(UUID id) {
        Optional<Movie> opt = movieRepository.findById(id);
        return opt.map(this::map).orElse(null);
    }

    private MovieDTO map(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setYear(movie.getYear());
        dto.setImdb(movie.getImdb());
        dto.setDirector(map(movie.getDirector()));
        dto.setActors(map(movie.getActors()));
        return dto;
    }

    private PersonDTO map(Director director) {
        PersonDTO dto = new PersonDTO();
        dto.setId(director.getId());
        dto.setFirstName(director.getFirstName());
        dto.setLastName(director.getLastName());
        dto.setBirthDate(director.getBirthDate());
        dto.setCountry(director.getCountry());
        return dto;
    }

    private List<PersonDTO> map(List<Actor> list) {
        List<PersonDTO> result = new ArrayList<>();
        for (Actor actor : list) {
            PersonDTO dto = new PersonDTO();
            dto.setId(actor.getId());
            dto.setFirstName(actor.getFirstName());
            dto.setLastName(actor.getLastName());
            dto.setBirthDate(actor.getBirthDate());
            dto.setCountry(actor.getCountry());
            result.add(dto);
        }
        return result;
    }
}
