package com.TripPlan.TripPlanProject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TripmemberResponseDTO {
    private String plannum;
    private String userId;
    private String nickname;
    private int authority;
}
