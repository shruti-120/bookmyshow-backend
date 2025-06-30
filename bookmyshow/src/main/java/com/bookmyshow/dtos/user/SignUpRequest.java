package com.bookmyshow.dtos.user;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
