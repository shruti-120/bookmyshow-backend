package com.bookmyshow.controllers.user;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.movie.MovieResponseDTO;
import com.bookmyshow.services.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users/movies")
@RequiredArgsConstructor
public class UserMovieController {

    private final MovieService movieService;
    
    @GetMapping
    public List<MovieResponseDTO> getAllMoviesByCity(@RequestParam(name = "city") String city) {
        return movieService.getAllMoviesByCity(city);
    }

    @GetMapping("/{id}")
    public MovieResponseDTO getMovieById(@PathVariable(name = "id") Long id) {
        return movieService.getMovieById(id);
    }
}
