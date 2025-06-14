package com.bookmyshow.dtos.theatre;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
}
