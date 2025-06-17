package com.bookmyshow.services;

import java.util.List;

import com.bookmyshow.dtos.theatre.*;

public interface TheatreService {
    TheatreResponseDTO createTheatre(TheatreRequestDTO requestDTO);

    List<TheatreResponseDTO> getTheatresByMovieId(Long id);
}
