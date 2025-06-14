package com.bookmyshow.mappers;

import org.mapstruct.Mapper;

import com.bookmyshow.dtos.movie.MovieRequestDTO;
import com.bookmyshow.dtos.movie.MovieResponseDTO;
import com.bookmyshow.models.Movie;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toEntity(MovieRequestDTO dto);
    MovieResponseDTO toDTO(Movie movie);
} 