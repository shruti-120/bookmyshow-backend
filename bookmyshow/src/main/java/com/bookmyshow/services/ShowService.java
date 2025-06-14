package com.bookmyshow.services;

import com.bookmyshow.dtos.show.*;

public interface ShowService {
    public ShowResponseDTO addShow(ShowRequestDTO requestDTO);
}
