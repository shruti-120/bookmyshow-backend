package com.bookmyshow.services.impl;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.screen.*;
import com.bookmyshow.mappers.ScreenMapper;
import com.bookmyshow.models.Screen;
import com.bookmyshow.models.Theatre;
import com.bookmyshow.repositories.ScreenRepository;
import com.bookmyshow.repositories.TheatreRepository;
import com.bookmyshow.services.ScreenService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenMapper screenMapper ;

    public ScreenResponseDTO addScreenToTheatre(ScreenRequestDTO requestDTO, Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId)
        .orElseThrow(() -> new EntityNotFoundException("Theatre not found with id: " + theatreId));

        Screen screen = screenMapper.toEntity(requestDTO);
        screen.setTheatre(theatre);
        
        screen = screenRepository.save(screen);
        return screenMapper.toDTO(screen);
    }
}
