package com.bookmyshow.controllers.user;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.services.BookingService;
import com.bookmyshow.dtos.booking.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserBookingController {
    
    private final BookingService bookingService;

    @PostMapping("/shows/{showId}/bookings")
    public BookingResponseDTO bookSeats(@PathVariable("showId") Long showId, @RequestBody BookingRequestDTO requestDTO) {
        return bookingService.bookSeats(showId, requestDTO);
    }

    @GetMapping("{userId}/bookings")
    public List<BookingResponseDTO> getUserBookingHistory(@PathVariable("userId") Long userId) {
        return bookingService.getUserBookingHistory(userId);
    }

    @DeleteMapping("/bookings/{id}")
    public void cancelBooking(@PathVariable("id") Long id) {
        bookingService.cancelBooking(id);
    }
}
