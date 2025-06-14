package com.bookmyshow.dtos.show;

import java.time.LocalDateTime;
import java.util.Map;

import com.bookmyshow.dtos.movie.MovieResponseDTO;
import com.bookmyshow.dtos.screen.ScreenResponseDTO;
import com.bookmyshow.enums.SeatType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponseDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MovieResponseDTO movie;
    private ScreenResponseDTO screen;
    private Map<SeatType, Double> priceMap;
}
