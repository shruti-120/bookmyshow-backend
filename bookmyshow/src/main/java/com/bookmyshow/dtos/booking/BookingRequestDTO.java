package com.bookmyshow.dtos.booking;

import lombok.*;

import java.util.List;

@Data
public class BookingRequestDTO {
    private Long userId;
    private List<Long> seatIds;
}