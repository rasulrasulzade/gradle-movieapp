package com.company.movieapp.controller;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.model.request.MovieRequest;
import com.company.movieapp.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Tag(name = "Movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Get movie list")
    public ResponseEntity<List<MovieDTO>> getMovies(){
        return new ResponseEntity<>(movieService.getMovies(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new movie")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieRequest request){
        return new ResponseEntity<>(movieService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by id")
    public ResponseEntity<MovieDTO> createMovie(@PathVariable UUID id){
        return new ResponseEntity<>(movieService.getById(id), HttpStatus.OK);
    }

}
