package com.company.movieapp.service;

import com.company.movieapp.dto.ActorDto;
import com.company.movieapp.model.request.PersonRequest;

import java.util.List;
import java.util.UUID;

public interface ActorService {

    ActorDto save(PersonRequest request);

    List<ActorDto> getActors();

    ActorDto getActorById(UUID id);
}
