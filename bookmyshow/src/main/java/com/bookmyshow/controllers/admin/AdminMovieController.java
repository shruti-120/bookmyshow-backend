package com.bookmyshow.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.movie.*;
import com.bookmyshow.services.MovieService;

import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("api/v1/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {
    
    private final MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping 
    public MovieResponseDTO createMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        return movieService.createMovie(movieRequestDTO);
    }
}
