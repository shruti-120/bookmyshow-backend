package com.bookmyshow.mappers;

import org.mapstruct.Mapper;

import com.bookmyshow.dtos.screen.*;
import com.bookmyshow.models.Screen;

@Mapper(componentModel = "spring", uses = {TheatreMapper.class})
public interface ScreenMapper {
    Screen toEntity(ScreenRequestDTO requestDTO);
    ScreenResponseDTO toDTO(Screen screen);
}
