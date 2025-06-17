package com.bookmyshow.services;

import java.util.*;

import com.bookmyshow.dtos.seat.*;

public interface SeatService {
    
    public Map<String, List<SeatStatusDTO>> getSeatsForShow(Long showId);
}
