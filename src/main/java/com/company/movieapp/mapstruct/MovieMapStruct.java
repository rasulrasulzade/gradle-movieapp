package com.company.movieapp.mapstruct;

import com.company.movieapp.dto.MovieDTO;
import com.company.movieapp.dto.PersonDTO;
import com.company.movieapp.entity.Actor;
import com.company.movieapp.entity.Director;
import com.company.movieapp.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MovieMapStruct {

    public abstract MovieDTO map(Movie movie);

    public abstract PersonDTO map(Director director);

    public abstract List<PersonDTO> mapToPersonDTOList(List<Actor> list);

    public abstract List<MovieDTO> mapToMovieDTOList(List<Movie> movieList);
}
