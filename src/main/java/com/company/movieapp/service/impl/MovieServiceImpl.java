package com.company.movieapp.service.impl;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.entity.Movie;
import com.company.movieapp.exception.CustomException;
import com.company.movieapp.mapstruct.MovieMapStruct;
import com.company.movieapp.model.request.MovieRequest;
import com.company.movieapp.repository.MovieRepository;
import com.company.movieapp.service.ActorService;
import com.company.movieapp.service.DirectorService;
import com.company.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ActorService actorService;
    private final DirectorService directorService;

    private final MovieMapStruct mapStruct;

    @Override
    public List<MovieDTO> getMovies() {
        return mapStruct.mapToMovieDTOList(movieRepository.findAll());
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
        return mapStruct.map((movieRepository.save(movie)));
    }

    @Override
    public MovieDTO getById(UUID id) {
        Optional<Movie> opt = movieRepository.findById(id);
        return opt.map(this::map).orElseThrow(() -> new CustomException("Movie not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    private MovieDTO map(Movie movie) {
        return mapStruct.map(movie);
    }
}
