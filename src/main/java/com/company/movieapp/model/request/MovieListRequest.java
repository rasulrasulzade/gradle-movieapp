package com.company.movieapp.model.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MovieListRequest {
    private List<UUID> actorIds;

    private UUID directorId;
}
