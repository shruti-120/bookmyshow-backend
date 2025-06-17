package com.bookmyshow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.models.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    
    @Query("SELECT bs FROM BookingSeat bs WHERE bs.booking.show.id = :showId")
    List<BookingSeat> findByShowId(@Param("showId") Long showId);
}
