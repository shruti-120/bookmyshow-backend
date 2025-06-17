package com.bookmyshow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.models.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
    
    @Query("""
    SELECT s FROM Show s
    WHERE s.movie.id = :movieId AND s.screen.theatre.id = :id
    """)
    public List<Show> findShowsByTheatreAndMovieId(@Param("id") Long id, @Param("movieId") Long movieId);

    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId")
    public List<Show> findShowsByMovieId(@Param("movieId") Long movieId);
}
