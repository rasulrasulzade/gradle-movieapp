package com.company.movieapp.controller;

import com.company.movieapp.entity.Actor;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<Actor>> getActors(){
        return new ResponseEntity<>(actorService.getActors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody PersonRequest request){
        return new ResponseEntity<>(actorService.save(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> createActor(@PathVariable UUID id){
        return new ResponseEntity<>(actorService.getActorById(id), HttpStatus.OK);
    }
}
