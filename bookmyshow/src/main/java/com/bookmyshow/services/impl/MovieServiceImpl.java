package com.bookmyshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.movie.*;
import com.bookmyshow.mappers.MovieMapper;
import com.bookmyshow.models.Movie;
import com.bookmyshow.repositories.MovieRepository;
import com.bookmyshow.services.MovieService;
import com.bookmyshow.utils.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO requestDTO) {
        log.info("Adding movie: {}", requestDTO.getTitle());

        Movie movie = movieMapper.toEntity(requestDTO);
        movie = movieRepository.save(movie);

        log.info("Movie added with ID: {}", movie.getId());
        return movieMapper.toDTO(movie);
    }

    @Override
    public List<MovieResponseDTO> getAllMoviesByCity(String city) {
        log.info("Fetching all movies for city: {}", city);
        List<Movie> movies = movieRepository.findDistinctByCity(city);

        if (movies.isEmpty()) {
            log.warn("No movies found for city: {}", city);
            throw new ResourceNotFoundException("No movies found in city: " + city);
        }

        return movies.stream()
                     .map(movieMapper::toDTO)
                     .collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO getMovieById(Long id) {
        log.info("Fetching movie by ID: {}", id);

        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Movie not found with ID: {}", id);
                return new ResourceNotFoundException("Movie not found with ID: " + id);
            });
            
        return movieMapper.toDTO(movie);
    }
}
