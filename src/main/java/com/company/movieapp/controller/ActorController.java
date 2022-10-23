package com.company.movieapp.controller;

import com.company.movieapp.dto.ActorDto;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
@Tag(name = "Actor")
public class ActorController {
    private final ActorService actorService;

    @GetMapping
    @Operation(summary = "Get actor list")
    public ResponseEntity<List<ActorDto>> getActors(){
        return new ResponseEntity<>(actorService.getActors(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new actor")
    public ResponseEntity<ActorDto> createActor(@RequestBody PersonRequest request){
        return new ResponseEntity<>(actorService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get actor by id")
    public ResponseEntity<ActorDto> createActor(@PathVariable UUID id){
        return new ResponseEntity<>(actorService.getActorById(id), HttpStatus.OK);
    }
}
