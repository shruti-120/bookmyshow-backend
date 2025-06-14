package com.bookmyshow.services;

import com.bookmyshow.dtos.theatre.*;

public interface TheatreService {
    TheatreResponseDTO createTheatre(TheatreRequestDTO requestDTO);
}
