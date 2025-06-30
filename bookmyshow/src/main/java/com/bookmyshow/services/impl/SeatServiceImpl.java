package com.bookmyshow.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.seat.*;
import com.bookmyshow.mappers.SeatMapper;
import com.bookmyshow.models.*;
import com.bookmyshow.repositories.*;
import com.bookmyshow.services.SeatService;
import com.bookmyshow.utils.exceptionHandlers.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService{
    
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final BookedSeatRepository bookedSeatRepository;
    private final SeatMapper seatMapper;

    public Map<String, List<SeatStatusDTO>> getSeatsForShow(Long showId) {
        log.info("Fetching seats for show ID: {}", showId);


        Show show = showRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("Show not found for ID: {}", showId);
                    return new ResourceNotFoundException("Show not found with ID: " + showId);
                });
            
        List<Seat> allSeats = seatRepository.findByScreenId(show.getScreen().getId());
        log.debug("Found {} seats for screen ID: {}", allSeats.size(), show.getScreen().getId());

        List<BookedSeat> bookedSeats = bookedSeatRepository.findByShowId(showId);

        Set<Long> bookedSeatIds = bookedSeats.stream()
                                             .map(bs -> bs.getSeat().getId())
                                             .collect(Collectors.toSet());

        log.info("Total booked seats for show {}: {}", showId, bookedSeatIds.size());

        return allSeats.stream()
                       .map(seat -> seatMapper.toDto(seat, bookedSeatIds))
                       .collect(Collectors.groupingBy(dto -> dto.getSeatNumber().substring(0, 1)));

    }
}
