package com.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
