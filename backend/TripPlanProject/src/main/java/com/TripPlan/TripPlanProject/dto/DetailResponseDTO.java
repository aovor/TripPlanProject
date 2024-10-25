package com.TripPlan.TripPlanProject.dto;

import com.TripPlan.TripPlanProject.model.Tripplandetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailResponseDTO {
    private String plannum;
    private int tripdate;
    private String destination;
    private String vector;
    private int visit;
    private String memo;

    public Tripplandetail toEntity() {
        Tripplandetail detail = new Tripplandetail();
        detail.setPlannum(this.plannum);
        detail.setTripdate(this.tripdate);
        detail.setDestination(this.destination);
        detail.setVector(this.vector);
        detail.setVisit(this.visit);
        detail.setMemo(this.memo);
        return detail;
    }
}
