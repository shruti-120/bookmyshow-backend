package com.bookmyshow.mappers;

import java.util.Set;

import org.mapstruct.Mapper;

import com.bookmyshow.dtos.seat.SeatStatusDTO;
import com.bookmyshow.enums.SeatStatus;
import com.bookmyshow.models.Seat;

@Mapper(componentModel = "spring")
public class SeatMapper {
    
    public SeatStatusDTO toDto(Seat seat, Set<Long> bookedSeatIds) {
        return SeatStatusDTO.builder()
            .seatId(seat.getId())
            .seatNumber(seat.getSeatNumber())
            .seatType(seat.getSeatType())
            .seatStatus(bookedSeatIds.contains(seat.getId())
                ? SeatStatus.BOOKED
                : SeatStatus.AVAILABLE)
            .build();
    }
}
