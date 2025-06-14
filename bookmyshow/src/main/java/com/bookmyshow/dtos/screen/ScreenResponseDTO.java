package com.bookmyshow.dtos.screen;

import com.bookmyshow.dtos.theatre.TheatreResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponseDTO {
    private Long id;
    private int screenNumber;
    private int seatingCapacity;
    private TheatreResponseDTO theatre;
}
