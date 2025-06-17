package com.bookmyshow.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.seat.*;
import com.bookmyshow.mappers.SeatMapper;
import com.bookmyshow.models.*;
import com.bookmyshow.repositories.*;
import com.bookmyshow.services.SeatService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService{
    
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatMapper seatMapper;

    public Map<String, List<SeatStatusDTO>> getSeatsForShow(Long showId) {
        Show show = showRepository.findById(showId)
            .orElseThrow(() -> new EntityNotFoundException("Show not found with ID: "+ showId));
            
        List<Seat> allSeats = seatRepository.findByScreenId(show.getScreen().getId());
        List<BookingSeat> bookedSeats = bookingSeatRepository.findByShowId(showId);

        Set<Long> bookedSeatIds = bookedSeats.stream()
                                             .map(bs -> bs.getSeat().getId())
                                             .collect(Collectors.toSet());

        return allSeats.stream()
                       .map(seat -> seatMapper.toDto(seat, bookedSeatIds))
                       .collect(Collectors.groupingBy(dto -> dto.getSeatNumber().substring(0, 1)));

    }
}
