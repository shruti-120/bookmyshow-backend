package com.bookmyshow.dtos.screen;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenRequestDTO {

    @NotNull(message = "Screen number is required")
    @Min(value = 1, message = "Screen number must be at least 1")
    private int screenNumber;

    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be greater than 0")
    private int seatingCapacity;
}