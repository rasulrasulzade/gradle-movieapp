package com.company.movieapp.service;

import com.company.movieapp.entity.Country;

import java.util.UUID;

public interface CountryService {
    Country findById(UUID id);
}
