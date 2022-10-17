package com.company.movieapp.service;

import com.company.movieapp.entity.Actor;
import com.company.movieapp.model.request.PersonRequest;

import java.util.List;
import java.util.UUID;

public interface ActorService {

    Actor save(PersonRequest request);

    List<Actor> getActors();

    Actor getActorById(UUID id);
}
