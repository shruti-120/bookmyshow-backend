package com.bookmyshow.services.impl;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.movie.*;
import com.bookmyshow.mappers.MovieMapper;
import com.bookmyshow.models.Movie;
import com.bookmyshow.repositories.MovieRepository;
import com.bookmyshow.services.MovieService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO requestDTO) {
        Movie movie = movieMapper.toEntity(requestDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDTO(movie);
    }
}
