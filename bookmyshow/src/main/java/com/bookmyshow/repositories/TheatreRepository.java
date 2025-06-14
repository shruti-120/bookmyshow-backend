package com.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.models.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    
}
