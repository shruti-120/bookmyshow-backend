package com.bookmyshow.controllers.user;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.show.*;
import com.bookmyshow.services.ShowService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserShowController {
    
    private final ShowService showService;

    @GetMapping("theatres/{id}/movies/{movieId}/shows")
    public List<ShowResponseDTO> getShowsByTheatreAndMovieId(@PathVariable(name = "id") Long id, @PathVariable(name = "movieId") Long movieId) {
        return showService.getShowsByTheatreAndMovieId(id, movieId);
    }

    @GetMapping("/movies/{movieId}/shows")
    public List<ShowResponseDTO> getShowsByMovieId(@PathVariable(name = "movieId") Long movieId) {
        return showService.getShowsByMovieId(movieId);
    }
}
