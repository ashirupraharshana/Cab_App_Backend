package com.example.cab_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String message;
    private String userid;
    private String username;
    private String email;
    private int userrole;
    private String token;
}