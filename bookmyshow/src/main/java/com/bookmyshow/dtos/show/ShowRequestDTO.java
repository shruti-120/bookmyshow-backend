package com.bookmyshow.dtos.show;

import java.time.LocalDateTime;
import java.util.Map;

import com.bookmyshow.enums.SeatType;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowRequestDTO {

    @NotNull(message = "Start time cannot be null")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Movie ID cannot be null")
    @Positive(message = "Movie ID must be a positive number")
    private Long movieId;

    @NotNull(message = "Screen ID cannot be null")
    @Positive(message = "Screen ID must be a positive number")
    private Long screenId;

    @NotEmpty(message = "Price map cannot be empty")
    private Map<@NotNull(message = "Seat type cannot be null") SeatType,
                @NotNull(message = "Price cannot be null")
                @Positive(message = "Price must be a positive value") Double> priceMap;
}
