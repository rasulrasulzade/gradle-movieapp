package com.company.movieapp.controller;

import com.company.movieapp.entity.Director;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<Director>> getDirectors(){
        return new ResponseEntity<>(directorService.getDirectors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Director> createDirector(@RequestBody PersonRequest request){
        return new ResponseEntity<>(directorService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> createActor(@PathVariable UUID id){
        return new ResponseEntity<>(directorService.getDirectorId(id), HttpStatus.OK);
    }
}
