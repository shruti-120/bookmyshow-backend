package com.bookmyshow.controllers.user;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.theatre.TheatreResponseDTO;
import com.bookmyshow.services.TheatreService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("api/v1/users/movies/{id}/theatres")
@RequiredArgsConstructor
public class UserTheatreController {
    
    private final TheatreService theatreService;

    @GetMapping
    public List<TheatreResponseDTO> getTheatresByMovieId(@PathVariable(name = "id") Long id) {
        return theatreService.getTheatresByMovieId(id);
    }
}
