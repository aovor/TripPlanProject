package com.TripPlan.TripPlanProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private final String status;
    private final String message;

    public UserResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
