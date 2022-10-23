package com.company.movieapp.mapstruct;

import com.company.movieapp.dto.ActorDto;
import com.company.movieapp.entity.Actor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ActorMapStruct {

    public abstract ActorDto map(Actor actor);

    public abstract List<ActorDto> map(List<Actor> actors);
}
