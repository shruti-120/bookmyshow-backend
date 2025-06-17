package com.bookmyshow.services;

import java.util.List;

import com.bookmyshow.dtos.seat.*;

public interface SeatService {
    
    public List<SeatStatusDTO> getSeatsForShow(Long showId);
}
