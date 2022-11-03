package com.company.movieapp.controller;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.dto.PersonDTO;
import com.company.movieapp.entity.Actor;
import com.company.movieapp.entity.Country;
import com.company.movieapp.entity.Director;
import com.company.movieapp.entity.Movie;
import com.company.movieapp.model.request.MovieRequest;
import com.company.movieapp.repository.ActorRepository;
import com.company.movieapp.repository.DirectorRepository;
import com.company.movieapp.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(args = "--spring.profiles.active=test")
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY, refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class MovieControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Create new actor")
    @Test
    public void testCreateMovie() throws Exception {
        Director director = Director.builder()
                .id(UUID.randomUUID())
                .firstName("Brian")
                .lastName("De Palma")
                .birthDate(LocalDate.of(1940, Month.SEPTEMBER, 11))
                .country(new Country(UUID.fromString("839c9692-5595-4e9c-adc7-1f886eeade78"), "ENGLAND", "ENGLISH"))
                .build();
        directorRepository.save(director);

        Actor actor = Actor.builder()
                .id(UUID.randomUUID())
                .firstName("Al")
                .lastName("Pacino")
                .birthDate(LocalDate.of(1940, Month.APRIL, 25))
                .country(new Country(UUID.fromString("c3eea7f9-233b-4dda-8d22-86631e12be80"), "SPANIEN", "SPANISH"))
                .build();
        actorRepository.save(actor);

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setName("Scarface");
        movieRequest.setYear(1983);
        movieRequest.setImdb(8.3F);
        movieRequest.setDirectorId(director.getId());
        movieRequest.setDirectorId(director.getId());
        movieRequest.setActorIds(List.of(actor.getId()));

        ResultActions resultActions = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieRequest)));

        resultActions.andDo(print())
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        MovieDTO response = objectMapper.readValue(contentAsString, MovieDTO.class);

        if (!movieRequest.getName().equals(response.getName())
                || !movieRequest.getYear().equals(response.getYear())
                || !movieRequest.getImdb().equals(response.getImdb())
                || !movieRequest.getDirectorId().equals(response.getDirector().getId())
                || !movieRequest.getActorIds().equals(response.getActors().stream()
                .map(PersonDTO::getId)
                .collect(Collectors.toList())))
            fail();

    }

    @DisplayName("Get movie by id positive scenario")
    @Test
    public void testGetMovieByIdPositive() throws Exception {
        Director director = Director.builder()
                .id(UUID.randomUUID())
                .firstName("Brian")
                .lastName("De Palma")
                .birthDate(LocalDate.of(1940, Month.SEPTEMBER, 11))
                .country(new Country(UUID.fromString("839c9692-5595-4e9c-adc7-1f886eeade78"), "ENGLAND", "ENGLISH"))
                .build();
        directorRepository.save(director);

        Actor actor = Actor.builder()
                .id(UUID.randomUUID())
                .firstName("Al")
                .lastName("Pacino")
                .birthDate(LocalDate.of(1940, Month.APRIL, 25))
                .country(new Country(UUID.fromString("c3eea7f9-233b-4dda-8d22-86631e12be80"), "SPANIEN", "SPANISH"))
                .build();
        actorRepository.save(actor);

        Movie movie = Movie.builder()
                .id(UUID.randomUUID())
                .name("Scarface")
                .year(1983)
                .imdb(8.3F)
                .director(director)
                .actors(List.of(actor))
                .build();
        movieRepository.save(movie);

        ResultActions resultActions = mockMvc.perform(get("/movies/{id}", movie.getId()));

        resultActions.andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        MovieDTO response = objectMapper.readValue(contentAsString, MovieDTO.class);

        assertEquals(movie.getId(), response.getId());
        assertEquals(movie.getName(), response.getName());
        assertEquals(movie.getYear(), response.getYear());
        assertEquals(movie.getImdb(), response.getImdb());

        assertEquals(movie.getDirector().getId(), response.getDirector().getId());
        assertEquals(movie.getDirector().getFirstName(), response.getDirector().getFirstName());
        assertEquals(movie.getDirector().getLastName(), response.getDirector().getLastName());
        assertEquals(movie.getDirector().getBirthDate(), response.getDirector().getBirthDate());
        assertEquals(movie.getDirector().getCountry(), response.getDirector().getCountry());

        assertEquals(movie.getActors().size(), response.getActors().size());
        for (int i = 0; i < response.getActors().size(); i++) {
            PersonDTO actual = response.getActors().get(i);
            Actor expected = movie.getActors().get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getBirthDate(), actual.getBirthDate());
            assertEquals(expected.getCountry(), actual.getCountry());
        }
    }

    @DisplayName("Get movie by id positive negative")
    @Test
    public void testGetMovieByIdNegative() throws Exception {

        UUID movieId = UUID.randomUUID();
        ResultActions resultActions = mockMvc.perform(get("/movies/{id}", movieId));

        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Movie not found with id: " + movieId)));
    }
}
