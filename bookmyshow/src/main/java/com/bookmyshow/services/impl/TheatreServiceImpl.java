package com.bookmyshow.services.impl;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.theatre.*;
import com.bookmyshow.mappers.TheatreMapper;
import com.bookmyshow.models.Theatre;
import com.bookmyshow.repositories.TheatreRepository;
import com.bookmyshow.services.TheatreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final TheatreMapper theatreMapper;

    @Override
    public TheatreResponseDTO createTheatre(TheatreRequestDTO requestDTO) {
        Theatre theatre = theatreMapper.toEntity(requestDTO);
        theatre = theatreRepository.save(theatre);
        return theatreMapper.toDTO(theatre);
    }
}
