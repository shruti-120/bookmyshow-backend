package com.bookmyshow.dtos.movie;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDTO {
    private Long id;
    private String title;
    private String description;
    private int durationMinutes;
    private LocalDate releaseDate;
    private String genre;
    private String language;
}
