package com.bookmyshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.show.*;
import com.bookmyshow.mappers.ShowMapper;
import com.bookmyshow.models.Show;
import com.bookmyshow.repositories.*;
import com.bookmyshow.services.ShowService;
import com.bookmyshow.utils.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowServiceImpl implements ShowService{
    private final ShowRepository showRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ShowMapper showMapper;

    @Override
    public ShowResponseDTO addShow(ShowRequestDTO requestDTO) {
        log.info("Adding new show: movieId={}, screenId={}, start={}, end={}",
            requestDTO.getMovieId(), requestDTO.getScreenId(),
            requestDTO.getStartTime(), requestDTO.getEndTime());

        var movie = movieRepository.findById(requestDTO.getMovieId()).orElseThrow(() -> {
                    log.warn("Invalid movie ID: {}", requestDTO.getMovieId());
                    return new ResourceNotFoundException("Invalid movie ID: " + requestDTO.getMovieId());
                });

        var screen = screenRepository.findById(requestDTO.getScreenId()) .orElseThrow(() -> {
                    log.warn("Invalid screen ID: {}", requestDTO.getScreenId());
                    return new ResourceNotFoundException("Invalid screen ID: " + requestDTO.getScreenId());
                });

        Show show = Show.builder()
                    .startTime(requestDTO.getStartTime())
                    .endTime(requestDTO.getEndTime())
                    .movie(movie)
                    .screen(screen)
                    .priceMap(requestDTO.getPriceMap())
                    .build();

        show = showRepository.save(show);

        log.info("Show created with ID: {}", show.getId());
        return showMapper.toDTO(show);
    }

    @Override
    public List<ShowResponseDTO> getShowsByTheatreAndMovieId(Long id, Long movieId) {
        log.info("Fetching shows for theatreId={}, movieId={}", id, movieId);

        List<Show> shows = showRepository.findShowsByTheatreAndMovieId(id, movieId);
        if (shows == null || shows.isEmpty()) {
            log.warn("No shows found for theatreId={} and movieId={}", id, movieId);
            throw new ResourceNotFoundException("No shows found for the given theatre and movie ID.");
        }

        return shows.stream()
                       .map(showMapper::toDTO)
                       .collect(Collectors.toList());
    }

    @Override
    public List<ShowResponseDTO> getShowsByMovieId(Long movieId) {
        log.info("Fetching shows for movie ID: {}", movieId);
        
        List<Show> shows = showRepository.findShowsByMovieId(movieId);

        if (shows == null || shows.isEmpty()) {
            log.warn("No shows found for movie ID: {}", movieId);
            throw new ResourceNotFoundException("No shows found for movie ID: " + movieId);
        }
        return shows.stream()
                    .map(showMapper::toDTO)
                    .collect(Collectors.toList());

    }
}
