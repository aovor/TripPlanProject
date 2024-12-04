package com.TripPlan.TripPlanProject.PlanPackage.dto;

import com.TripPlan.TripPlanProject.PlanPackage.model.Planlist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlanlistResponseDTO {

    @Schema(hidden = true)
    private String plannum;

    private String userId;
    private String startDate;
    private String endDate;
    private int tripTotalDate;
    private String tripRegion;
    private String tripRoute;
    private boolean tripOpen;

    public Planlist toEntity() {
        Planlist planlist = new Planlist();
        planlist.setPlannum(this.plannum);
        planlist.setUserId(this.userId);
        planlist.setStartDate(this.startDate);
        planlist.setEndDate(this.endDate);
        planlist.setTripTotalDate(this.tripTotalDate);
        planlist.setTripRegion(this.tripRegion);
        planlist.setTripRoute(this.tripRoute);
        planlist.setTripOpen(this.tripOpen);
        return planlist;
    }
}
