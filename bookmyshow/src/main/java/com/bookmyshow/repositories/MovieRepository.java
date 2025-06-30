package com.bookmyshow.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    @Query("SELECT DISTINCT m FROM Movie m JOIN m.shows s JOIN s.screen sc JOIN sc.theatre t WHERE t.city = :city")
    List<Movie> findDistinctByCity(@Param("city") String city);

    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);

}
