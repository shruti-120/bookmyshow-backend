package com.bookmyshow.services;

import com.bookmyshow.dtos.screen.ScreenRequestDTO;
import com.bookmyshow.dtos.screen.ScreenResponseDTO;

public interface ScreenService {
    ScreenResponseDTO addScreenToTheatre(ScreenRequestDTO requestDTO, Long id);
}
