package com.bookmyshow.dtos.screen;

import java.util.*;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenRequestDTO {

    @NotNull(message = "Screen number is required")
    @Min(value = 1, message = "Screen number must be at least 1")
    private int screenNumber;

    @NotNull(message = "Number of rows is required")
    @Positive(message = "Number of rows must be greater than 0")
    private int rows;

    @NotNull(message = "Number of columns is required")
    @Positive(message = "Number of columns must be greater than 0")
    private int columns;

    private Map<String, List<String>> seatTypeMapping;
}