package com.bookmyshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MovieResponseDTO> getAllMoviesByCity(String city) {
        List<Movie> movies = movieRepository.findDistinctByCity(city);

        return movies.stream()
                     .map(movieMapper::toDTO)
                     .collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
        return movieMapper.toDTO(movie);
    }
}
