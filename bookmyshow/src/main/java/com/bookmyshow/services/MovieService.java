package com.bookmyshow.services;

import com.bookmyshow.dtos.movie.*;

public interface MovieService {
    MovieResponseDTO createMovie(MovieRequestDTO requestDTO);
}
