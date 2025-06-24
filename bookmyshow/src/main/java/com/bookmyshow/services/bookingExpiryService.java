package com.bookmyshow.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bookmyshow.enums.BookingStatus;
import com.bookmyshow.models.BookedSeat;
import com.bookmyshow.models.Booking;
import com.bookmyshow.repositories.BookedSeatRepository;
import com.bookmyshow.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class bookingExpiryService {
    
    private final BookingRepository bookingRepository;
    private final BookedSeatRepository bookedSeatRepository;
 
    @Scheduled(fixedRate = 60000) //Runs every minute
    public void expireOldBookings() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(10);

        List<Booking> expiredBookings = bookingRepository.findByStatusAndBookingTimeBefore(BookingStatus.PENDING, expiryTime);

        if (expiredBookings.isEmpty()) {
            log.info("No expired bookings found at {}", LocalDateTime.now());
        }

        for(Booking booking: expiredBookings) {
            List<BookedSeat> bookedSeats = bookedSeatRepository.findByBookingId(booking.getId());
            bookedSeatRepository.deleteAll(bookedSeats);
            booking.setStatus(BookingStatus.CANCELLED);

            log.debug("Booking ID {} expired and {} seats removed", booking.getId(), bookedSeats.size());
        }

        bookingRepository.saveAll(expiredBookings);
    }
}
