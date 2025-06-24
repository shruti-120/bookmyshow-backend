package com.bookmyshow.services.impl;

import java.util.*;

import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.screen.*;
import com.bookmyshow.enums.SeatType;
import com.bookmyshow.mappers.ScreenMapper;
import com.bookmyshow.models.*;
import com.bookmyshow.repositories.*;
import com.bookmyshow.services.ScreenService;
import com.bookmyshow.utils.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreenServiceImpl implements ScreenService {
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final SeatRepository seatRepository;
    private final ScreenMapper screenMapper ;

    @Override
    public ScreenResponseDTO addScreenToTheatre(ScreenRequestDTO requestDTO, Long theatreId) {
        log.info("Adding screen to theatre ID: {}", theatreId);

        // Step 1: Fetch Theatre
        Theatre theatre = theatreRepository.findById(theatreId)
            .orElseThrow(() -> {
                log.warn("Theatre not found with ID: {}", theatreId);
                return new ResourceNotFoundException("Theatre not found with id: " + theatreId);
            });

        // Step 2: Create Screen entity and set theatre
        Screen screen = screenMapper.toEntity(requestDTO);
        screen.setTheatre(theatre);
        screen = screenRepository.save(screen); // save to get ID
        log.info("Screen saved with ID: {}", screen.getId());

        // Step 3: Map each row (A, B, ...) to SeatType from the request
        Map<String, SeatType> rowToType = new HashMap<>();
        requestDTO.getSeatTypeMapping().forEach((seatTypeStr, rows) -> {
            SeatType seatType = SeatType.valueOf(seatTypeStr.toUpperCase());
            for (String row : rows) {
                rowToType.put(row.toUpperCase(), seatType);
            }
        });

        // Step 4: Generate seats for each row and column
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < requestDTO.getRows(); i++) {
            char rowChar = (char) ('A' + i);
            String row = String.valueOf(rowChar);

            SeatType seatType = rowToType.getOrDefault(row, SeatType.REGULAR); // fallback

            for (int j = 1; j <= requestDTO.getColumns(); j++) {
                String seatNumber = row + j;

                seats.add(Seat.builder()
                    .seatNumber(seatNumber)
                    .seatType(seatType)
                    .screen(screen)
                    .build());
            }
        }

        // Step 5: Persist all seats
        seatRepository.saveAll(seats);
        log.info("Generated and saved {} seats for screen ID: {}", seats.size(), screen.getId());

        // Step 6: Return DTO
        return screenMapper.toDTO(screen);
    }
}
