package com.bookmyshow.services;

import java.util.List;

import com.bookmyshow.dtos.show.*;

public interface ShowService {
    public ShowResponseDTO addShow(ShowRequestDTO requestDTO);

    public List<ShowResponseDTO> getShowsByTheatreAndMovieId(Long id, Long movieId);

    public List<ShowResponseDTO> getShowsByMovieId(Long movieId);
}
