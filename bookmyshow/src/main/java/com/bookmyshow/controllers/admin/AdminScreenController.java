package com.bookmyshow.controllers.admin;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.dtos.screen.*;
import com.bookmyshow.services.ScreenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/admin/theaters/{id}/screens")
@RequiredArgsConstructor
public class AdminScreenController {
    private final ScreenService screenService;

    @PostMapping 
    public ScreenResponseDTO createScreen(@PathVariable(name = "id") Long id, @Valid @RequestBody ScreenRequestDTO screenRequestDTO) {
        return screenService.addScreenToTheatre(screenRequestDTO, id);
    }
}
