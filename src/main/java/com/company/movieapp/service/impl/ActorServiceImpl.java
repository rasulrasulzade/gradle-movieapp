package com.company.movieapp.service.impl;

import com.company.movieapp.entity.Actor;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.repository.ActorRepository;
import com.company.movieapp.repository.CountryRepository;
import com.company.movieapp.service.ActorService;
import com.company.movieapp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final CountryService countryService;


    @Override
    public Actor save(PersonRequest request) {
        Actor entity = new Actor();
        entity.setId(UUID.randomUUID());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setCountry(countryService.findById(request.getCountryId()));
        return actorRepository.save(entity);
    }

    @Override
    public List<Actor> getActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor getActorById(UUID id) {
        return actorRepository.findById(id).orElse(null);
    }
}
