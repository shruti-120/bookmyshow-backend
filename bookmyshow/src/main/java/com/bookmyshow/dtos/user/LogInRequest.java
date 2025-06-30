package com.bookmyshow.dtos.user;

import lombok.Data;

@Data
public class LogInRequest {
    private String email;
    private String password;
}
