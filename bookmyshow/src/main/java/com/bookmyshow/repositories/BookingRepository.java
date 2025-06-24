package com.bookmyshow.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.enums.BookingStatus;
import com.bookmyshow.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStatusAndBookingTimeBefore(BookingStatus status, LocalDateTime time);

    List<Booking> findByUserId(@Param("userId") Long userId);
}
