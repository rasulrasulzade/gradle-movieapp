package com.company.movieapp.controller;

import com.company.movieapp.entity.Director;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
@Tag(name = "Director")
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    @Operation(summary = "Get director list")
    public ResponseEntity<List<Director>> getDirectors(){
        return new ResponseEntity<>(directorService.getDirectors(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new director")
    public ResponseEntity<Director> createDirector(@RequestBody PersonRequest request){
        return new ResponseEntity<>(directorService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get director by id")
    public ResponseEntity<Director> createActor(@PathVariable UUID id){
        return new ResponseEntity<>(directorService.getDirectorId(id), HttpStatus.OK);
    }
}
