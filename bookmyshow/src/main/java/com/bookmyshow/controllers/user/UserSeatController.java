package com.bookmyshow.controllers.user;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.seat.*;
import com.bookmyshow.services.SeatService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserSeatController {
    
    private final SeatService seatService;

    @GetMapping("shows/{showId}/seats")
    public List<SeatStatusDTO> getSeatsForShow(@PathVariable(name = "showId") Long showId) {
        return seatService.getSeatsForShow(showId);
    }
}
