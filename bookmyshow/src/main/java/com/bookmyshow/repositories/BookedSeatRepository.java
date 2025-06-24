package com.bookmyshow.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.models.BookedSeat;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, Long> {
    
    @Query("SELECT bs FROM BookedSeat bs WHERE bs.booking.show.id = :showId")
    List<BookedSeat> findByShowId(@Param("showId") Long showId);

    @Query("SELECT bs.seat.id FROM BookedSeat bs WHERE bs.show.id = :showId AND bs.booking.status IN ('PENDING', 'CONFIRMED')")
    Set<Long> findSeatIdsByShowId(@Param("showId") Long showId);

    @Query("SELECT bs FROM BookedSeat bs WHERE bs.booking.id = :bookingId")
    List<BookedSeat> findByBookingId(@Param("bookingId") Long bookingId);

}
