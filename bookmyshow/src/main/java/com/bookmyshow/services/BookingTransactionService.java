package com.bookmyshow.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookmyshow.models.Booking;
import com.bookmyshow.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingTransactionService {

    private final BookingRepository bookingRepository;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Booking createPendingBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
}
