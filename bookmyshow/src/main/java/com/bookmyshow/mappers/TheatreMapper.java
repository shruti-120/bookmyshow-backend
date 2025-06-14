package com.bookmyshow.mappers;

import org.mapstruct.Mapper;

import com.bookmyshow.dtos.theatre.*;

import com.bookmyshow.models.Theatre;

@Mapper(componentModel = "spring")
public interface TheatreMapper {
    Theatre toEntity(TheatreRequestDTO theatreRequestDTO);
    TheatreResponseDTO toDTO(Theatre theatre);
}
