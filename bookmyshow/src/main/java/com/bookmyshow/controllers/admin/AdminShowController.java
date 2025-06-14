package com.bookmyshow.controllers.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.dtos.show.ShowRequestDTO;
import com.bookmyshow.dtos.show.ShowResponseDTO;
import com.bookmyshow.services.ShowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/admin/shows")
@RequiredArgsConstructor
public class AdminShowController {
    private final ShowService showService;

    @PostMapping
    public ShowResponseDTO addShow(@Valid @RequestBody ShowRequestDTO requestDTO) {
        return showService.addShow(requestDTO);
    }
}