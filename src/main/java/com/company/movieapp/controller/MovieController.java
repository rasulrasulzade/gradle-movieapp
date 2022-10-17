package com.company.movieapp.controller;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.model.request.MovieRequest;
import com.company.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovies(){
        return new ResponseEntity<>(movieService.getMovies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieRequest request){
        return new ResponseEntity<>(movieService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> createMovie(@PathVariable UUID id){
        return new ResponseEntity<>(movieService.getById(id), HttpStatus.OK);
    }

}
