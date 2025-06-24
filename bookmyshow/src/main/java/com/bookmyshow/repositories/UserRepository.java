package com.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
