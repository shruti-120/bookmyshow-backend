package com.bookmyshow.mappers;

import org.mapstruct.Mapper;

import com.bookmyshow.dtos.show.*;
import com.bookmyshow.models.Show;

@Mapper(componentModel = "spring", uses = {MovieMapper.class, ScreenMapper.class})
public interface ShowMapper {
    ShowResponseDTO toDTO(Show show);
}
