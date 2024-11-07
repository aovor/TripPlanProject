package com.TripPlan.TripPlanProject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDTO {
    private final String status;
    private final String message;
    private final String token;

    public LoginResponseDTO(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }
}
