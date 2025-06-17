package com.bookmyshow.services;

import java.util.List;

import com.bookmyshow.dtos.movie.*;

public interface MovieService {
    MovieResponseDTO createMovie(MovieRequestDTO requestDTO);

    List<MovieResponseDTO> getAllMoviesByCity(String city);

    MovieResponseDTO getMovieById(Long id);
}
