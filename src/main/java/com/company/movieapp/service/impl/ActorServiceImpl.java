package com.company.movieapp.service.impl;

import com.company.movieapp.dto.ActorDto;
import com.company.movieapp.entity.Actor;
import com.company.movieapp.exception.CustomException;
import com.company.movieapp.mapstruct.ActorMapStruct;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.repository.ActorRepository;
import com.company.movieapp.service.ActorService;
import com.company.movieapp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final CountryService countryService;

    private final ActorMapStruct actorMapStruct;


    @Override
    public ActorDto save(PersonRequest request) {
        Actor entity = new Actor();
        entity.setId(UUID.randomUUID());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setCountry(countryService.findById(request.getCountryId()));
        return actorMapStruct.map(actorRepository.save(entity));
    }

    @Override
    public List<ActorDto> getActors() {
        return actorMapStruct.map(actorRepository.findAll());
    }

    @Override
    public ActorDto getActorById(UUID id) {
        Optional<Actor> opt = actorRepository.findById(id);
        return opt.map(actorMapStruct::map).orElseThrow(() -> new CustomException("Actor not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}
