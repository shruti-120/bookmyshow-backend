package com.bookmyshow.dtos.seat;

import com.bookmyshow.enums.*;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatStatusDTO {
    private Long seatId;
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus; 
}
