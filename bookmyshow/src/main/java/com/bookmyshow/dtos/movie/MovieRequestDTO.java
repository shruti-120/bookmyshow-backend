package com.bookmyshow.dtos.movie;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 500, message = "Duration must be less than or equal to 500 minutes")
    private int durationMinutes;

    @NotNull(message = "Release date is required")
    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

    @NotBlank(message = "Genre cannot be blank")
    @Size(max = 50, message = "Genre must be less than 50 characters")
    private String genre;

    @NotBlank(message = "Language cannot be blank")
    @Size(max = 50, message = "Language must be less than 50 characters")
    private String language;
}
