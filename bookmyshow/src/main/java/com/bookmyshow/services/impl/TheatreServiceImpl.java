package com.bookmyshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.theatre.*;
import com.bookmyshow.mappers.TheatreMapper;
import com.bookmyshow.models.Theatre;
import com.bookmyshow.repositories.TheatreRepository;
import com.bookmyshow.services.TheatreService;
import com.bookmyshow.utils.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final TheatreMapper theatreMapper;

    @Override
    public TheatreResponseDTO createTheatre(TheatreRequestDTO requestDTO) {
        log.info("Adding theatre with name: {}", requestDTO.getName());

        Theatre theatre = theatreMapper.toEntity(requestDTO);
        theatre = theatreRepository.save(theatre);

        log.info("Theatre created with ID: {}", theatre.getId());
        return theatreMapper.toDTO(theatre);
    }

    @Override
    public List<TheatreResponseDTO> getTheatresByMovieId(Long id) {
        log.info("Fetching theatres for movie ID: {}", id);

        List<Theatre> theatres = theatreRepository.findTheatreByMovieId(id);

        if(theatres == null || theatres.isEmpty()) {
            log.warn("No theatres found for movie ID: {}", id);
            throw new ResourceNotFoundException("No theatres found for movie ID: "+ id);
        }

        log.info("Found {} theatres for movie ID: {}", theatres.size(), id);
        return theatres.stream()
                       .map(theatreMapper::toDTO)
                       .collect(Collectors.toList());
    }
}
