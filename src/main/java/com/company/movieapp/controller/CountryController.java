package com.company.movieapp.controller;

import com.company.movieapp.entity.Country;
import com.company.movieapp.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
@Tag(name = "Country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    @Operation(summary = "Get country list")
    public ResponseEntity<List<Country>> getCountries(){
        return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    }
}
