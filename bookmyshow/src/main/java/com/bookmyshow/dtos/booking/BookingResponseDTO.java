package com.bookmyshow.dtos.booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bookmyshow.enums.BookingStatus;

import lombok.*;

@Data
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private List<String> seatNumbers;
    private BookingStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime bookingTime;
}
