package com.bookmyshow.dtos.screen;

import com.bookmyshow.dtos.theatre.TheatreResponseDTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponseDTO {
    private Long id;
    private int screenNumber;
    private int rows;
    private int columns;
    private TheatreResponseDTO theatre;
}
