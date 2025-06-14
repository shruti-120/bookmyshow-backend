package com.bookmyshow.dtos.theatre;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreRequestDTO {

    @NotBlank(message = "Theatre name cannot be blank")
    @Size(max = 100, message = "Theatre name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 200, message = "Address must be less than 200 characters")
    private String address;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 50, message = "City name must be less than 50 characters")
    private String city;
}
