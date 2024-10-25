package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.model.Tripplandetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlandetailRepository extends JpaRepository<Tripplandetail, Tripplandetail.TripplandetailId> {
    List<Tripplandetail> findByPlannum(String plannum);
}