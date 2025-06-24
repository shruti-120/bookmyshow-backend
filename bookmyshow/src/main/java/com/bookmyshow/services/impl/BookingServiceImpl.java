package com.bookmyshow.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.booking.*;
import com.bookmyshow.enums.BookingStatus;
import com.bookmyshow.enums.SeatType;
import com.bookmyshow.models.*;
import com.bookmyshow.repositories.*;
import com.bookmyshow.services.BookingService;
import com.bookmyshow.services.BookingTransactionService;
import com.bookmyshow.utils.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final BookedSeatRepository bookedSeatRepository; 
    private final SeatRepository seatRepository;

    private final BookingTransactionService bookingTransactionService;

    private static final Long GUEST_USER_ID = 999L;

    @Override
    @Transactional
    public BookingResponseDTO bookSeats(Long showId, BookingRequestDTO bookingRequestDTO) {
        log.info("Received booking request for show ID: {} by user: {}", showId, bookingRequestDTO.getUserId());

        Show show = showRepository.findById(showId)
            .orElseThrow(() -> {
                log.warn("Show not found: {}", showId);
                return new ResourceNotFoundException("Show not found with ID: " + showId);
            });

        Long userId = (bookingRequestDTO.getUserId() != null) ? bookingRequestDTO.getUserId() : GUEST_USER_ID;

        List<Seat> seats = seatRepository.findSeats(bookingRequestDTO.getSeatIds());

        Set<Long> alreadyBooked = bookedSeatRepository.findSeatIdsByShowId(showId);
        for(Seat seat: seats) {
            if (alreadyBooked.contains(seat.getId())) {
                log.warn("Seat already booked: {} (ID: {})", seat.getSeatNumber(), seat.getId());
                throw new IllegalStateException("Seat already booked: " + seat.getSeatNumber());
            }
        }

        Booking booking  = Booking.builder()
                                  .userId(userId)
                                  .show(show)
                                  .status(BookingStatus.PENDING)
                                  .bookingTime(LocalDateTime.now())
                                  .build();
        
        booking = bookingTransactionService.createPendingBooking(booking);

        List<BookedSeat> bookedSeats = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        Map<SeatType, BigDecimal> priceMap = show.getPriceMap();

        for(Seat seat: seats) {
            BigDecimal price = priceMap.get(seat.getSeatType());
            
            bookedSeats.add(BookedSeat.builder()
                       .booking(booking)
                       .show(show)
                       .seat(seat)
                       .pricePaid(price)
                       .build());
            totalAmount = totalAmount.add(price);
        }

        bookedSeatRepository.saveAll(bookedSeats);
        booking.setBookedSeats(bookedSeats);
        booking.setTotalAmount(totalAmount);

        log.info("Booking created successfully: ID={}, User={}, Seats={}, Total={}",
                booking.getId(), userId, seats.size(), totalAmount);

        return BookingResponseDTO.builder()
                    .bookingId(booking.getId())
                    .seatNumbers(seats.stream().map(Seat::getSeatNumber).toList())
                    .status(booking.getStatus())
                    .totalAmount(totalAmount)
                    .bookingTime(booking.getBookingTime())
                    .build();
    }

    public void cancelBooking(Long id) {
        log.info("Attempting to cancel booking ID: {}", id);

        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Booking not found: {}", id);
                return new ResourceNotFoundException("Booking not found for ID: " + id);
            });

        if (booking.getStatus() == BookingStatus.PENDING) {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            log.info("Booking cancelled: {}", id);
        } else {
            log.warn("Cannot cancel booking with status: {}", booking.getStatus());
            throw new IllegalStateException("Only PENDING bookings can be cancelled.");
        }
    }

    public List<BookingResponseDTO> getUserBookingHistory(Long userId) {
        log.info("Fetching booking history for user: {}", userId);
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        List<BookingResponseDTO> userBookingHistory = new ArrayList<>();

        for(Booking booking: bookings) {
            List<String> seatNumbers = booking.getBookedSeats()
                                              .stream()
                                              .map(bookedSeat -> bookedSeat.getSeat().getSeatNumber())
                                              .collect(Collectors.toList());
            userBookingHistory.add(BookingResponseDTO.builder()
                            .bookingId(booking.getId())
                            .seatNumbers(seatNumbers)
                            .status(booking.getStatus())
                            .totalAmount(booking.getTotalAmount())
                            .bookingTime(booking.getBookingTime())
                            .build());
        }

        log.info("Found {} bookings for user ID: {}", userBookingHistory.size(), userId);
        return userBookingHistory;
    }
}
