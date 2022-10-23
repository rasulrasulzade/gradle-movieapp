package com.company.movieapp.repository;

import com.company.movieapp.entity.Movie;
import com.company.movieapp.inter.MovieInter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    @Query(value = "SELECT m.id,\n" +
            "       m.name,\n" +
            "       m.imdb,\n" +
            "       m.year,\n" +
            "       d.id         AS directorId,\n" +
            "       d.first_name || ' ' || d.last_name AS directorFullName\n" +
            "FROM movie_db.movie m\n" +
            "         JOIN movie_db.director d ON m.director_id = d.id\n" +
            "         JOIN movie_db.movie_actor ma ON m.id = ma.movie_id\n" +
            "         JOIN movie_db.actor a ON a.id = ma.actor_id\n" +
            "WHERE (:dirId IS NULL OR CAST(m.director_id AS varchar) = :dirId) AND (COALESCE(:actorIds) IS NULL OR CAST(a.id AS varchar) IN (:actorIds))", nativeQuery = true)
    List<MovieInter> findByDirectorIdAndActorIds(@Param("dirId") String directorId, @Param("actorIds") List<String> actorIds);
}
