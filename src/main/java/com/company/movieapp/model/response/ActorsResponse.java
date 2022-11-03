package com.company.movieapp.model.response;

import com.company.movieapp.dto.ActorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorsResponse {
    private List<ActorDto> actors;
}
