package com.bookmyshow.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.theatre.*;
import com.bookmyshow.services.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/admin/theatres")
@RequiredArgsConstructor
public class AdminTheatreController {
    
    private final TheatreService theatreService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public TheatreResponseDTO createTheatre(@Valid @RequestBody TheatreRequestDTO RequestDTO) {
        return theatreService.createTheatre(RequestDTO);
    }
}
