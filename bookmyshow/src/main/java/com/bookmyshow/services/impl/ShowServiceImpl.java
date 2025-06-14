package com.bookmyshow.services.impl;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.show.*;
import com.bookmyshow.mappers.ShowMapper;
import com.bookmyshow.models.Show;
import com.bookmyshow.repositories.*;
import com.bookmyshow.services.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService{
    private final ShowRepository showRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ShowMapper showMapper;

    @Override
    public ShowResponseDTO addShow(ShowRequestDTO requestDTO) {
        var movie = movieRepository.findById(requestDTO.getMovieId()).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        var screen = screenRepository.findById(requestDTO.getScreenId()).orElseThrow(() -> new IllegalArgumentException("Invalid screen ID"));

        Show show = Show.builder()
                    .startTime(requestDTO.getStartTime())
                    .endTime(requestDTO.getEndTime())
                    .movie(movie)
                    .screen(screen)
                    .priceMap(requestDTO.getPriceMap())
                    .build();

        show = showRepository.save(show);
        return showMapper.toDTO(show);
    }
}
