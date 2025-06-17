package com.bookmyshow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.models.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{
    List<Seat> findByScreenId(Long screenId);
}
