package com.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.models.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
    
}
