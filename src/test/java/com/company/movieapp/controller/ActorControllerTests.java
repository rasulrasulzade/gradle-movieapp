package com.company.movieapp.controller;

import com.company.movieapp.dto.ActorDto;
import com.company.movieapp.entity.Actor;
import com.company.movieapp.entity.Country;
import com.company.movieapp.model.request.PersonRequest;
import com.company.movieapp.model.response.ActorsResponse;
import com.company.movieapp.repository.ActorRepository;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(args = "--spring.profiles.active=test")
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY, refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ActorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Create new actor")
    @Test
    public void testCreateActor() throws Exception {
        // given - precondition or setup
        PersonRequest personRequest = new PersonRequest();
        personRequest.setFirstName("Anthony");
        personRequest.setLastName("Hopkins");
        personRequest.setBirthDate(LocalDate.of(1937, Month.DECEMBER, 31));
        personRequest.setCountryId(UUID.fromString("839c9692-5595-4e9c-adc7-1f886eeade78"));

        // when - action or behaviour that we are going test
        ResultActions resultActions = mockMvc.perform(post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personRequest)));

        // then - verify the result or output using assert statements
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(personRequest.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(personRequest.getLastName())));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        ActorDto response = objectMapper.readValue(contentAsString, ActorDto.class);

        assertEquals(personRequest.getBirthDate(), response.getBirthDate());
        assertEquals(personRequest.getCountryId(), response.getCountry().getId());

    }

    @DisplayName("Get actor by id positive scenario")
    @Test
    public void testGetActorByIdPositive() throws Exception {
        Actor actor = new Actor();
        actor.setId(UUID.randomUUID());
        actor.setFirstName("Anthony");
        actor.setFirstName("Hopkins");
        actor.setBirthDate(LocalDate.of(1937, Month.DECEMBER, 31));
        actor.setCountry(new Country(UUID.fromString("839c9692-5595-4e9c-adc7-1f886eeade78"), "ENGLAND", "ENGLISH"));
        actorRepository.save(actor);

        ResultActions resultActions = mockMvc.perform(get("/actors/{id}", actor.getId()));
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        ActorDto response = objectMapper.readValue(contentAsString, ActorDto.class);

        //assertEquals(expected, actual)

        assertEquals(actor.getId(), response.getId());
        assertEquals(actor.getFirstName(), response.getFirstName());
        assertEquals(actor.getLastName(), response.getLastName());
        assertEquals(actor.getBirthDate(), response.getBirthDate());
        assertEquals(actor.getCountry(), response.getCountry());
    }

    @DisplayName("Get actor by id negative scenario")
    @Test
    public void testGetActorByIdNegative() throws Exception {

        UUID actorId = UUID.randomUUID();
        ResultActions resultActions = mockMvc.perform(get("/actors/{id}", actorId));

        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Actor not found with id: " + actorId)));
    }

    @DisplayName("Get all actors")
    @Test
    public void testGetAllActors() throws Exception {
        Actor actor1 = new Actor();
        actor1.setId(UUID.randomUUID());
        actor1.setFirstName("Anthony");
        actor1.setFirstName("Hopkins");
        actor1.setBirthDate(LocalDate.of(1937, Month.DECEMBER, 31));
        actor1.setCountry(new Country(UUID.fromString("839c9692-5595-4e9c-adc7-1f886eeade78"), "ENGLAND", "ENGLISH"));

        Actor actor2 = new Actor();
        actor2.setId(UUID.randomUUID());
        actor2.setFirstName("Leonardo");
        actor2.setFirstName("DiCaprio");
        actor2.setBirthDate(LocalDate.of(1974, Month.NOVEMBER, 11));
        actor2.setCountry(new Country(UUID.fromString("839c9692-5595-4e9c-adc7-1f886eeade78"), "ENGLAND", "ENGLISH"));

        List<Actor> actorList = List.of(actor1, actor2);
        actorRepository.saveAll(actorList);

        ResultActions resultActions = mockMvc.perform(get("/actors"));

        resultActions.andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        ActorsResponse response = objectMapper.readValue(contentAsString, ActorsResponse.class);
        List<ActorDto> responseList = response.getActors();

        assertEquals(actorList.size(), responseList.size());
        for (int i = 0; i < responseList.size(); i++) {
            ActorDto actual = responseList.get(i);
            Actor expected = actorList.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getBirthDate(), actual.getBirthDate());
            assertEquals(expected.getCountry(), actual.getCountry());
        }
    }
}
