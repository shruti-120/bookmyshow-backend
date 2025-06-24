package com.bookmyshow.services;

import java.util.List;

import com.bookmyshow.dtos.booking.*;

public interface BookingService {
    public BookingResponseDTO bookSeats(Long showId, BookingRequestDTO bookingRequestDTO);

    public void cancelBooking(Long id);

    public List<BookingResponseDTO> getUserBookingHistory(Long userId);
}

