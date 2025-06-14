package com.bookmyshow.repositories;

import com.bookmyshow.models.Screen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    
}
