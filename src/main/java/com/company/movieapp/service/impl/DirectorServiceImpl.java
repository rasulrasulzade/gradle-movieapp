package com.company.movieapp.service.impl;

import com.company.movieapp.entity.Director;
import com.company.movieapp.exception.CustomException;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.repository.DirectorRepository;
import com.company.movieapp.service.CountryService;
import com.company.movieapp.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final CountryService countryService;

    @Override
    public Director save(PersonRequest request) {
        Director entity = new Director();
        entity.setId(UUID.randomUUID());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setCountry(countryService.findById(request.getCountryId()));
        return directorRepository.save(entity);
    }

    @Override
    public List<Director> getDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public Director getDirectorId(UUID id) {
        return directorRepository.findById(id).orElseThrow(() -> new CustomException("Director not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}
