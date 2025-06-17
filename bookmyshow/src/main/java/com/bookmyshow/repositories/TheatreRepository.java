package com.bookmyshow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmyshow.models.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    @Query("SELECT DISTINCT s.theatre FROM Show sh JOIN sh.screen s WHERE sh.movie.id = :movieId")
    List<Theatre> findTheatreByMovieId(@Param("movieId") Long id);
    
}
