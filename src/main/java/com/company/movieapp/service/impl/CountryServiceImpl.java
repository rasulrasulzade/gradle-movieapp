package com.company.movieapp.service.impl;

import com.company.movieapp.entity.Country;
import com.company.movieapp.repository.CountryRepository;
import com.company.movieapp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Country findById(UUID id) {
        return countryRepository.findById(id).orElse(null);
    }
}
