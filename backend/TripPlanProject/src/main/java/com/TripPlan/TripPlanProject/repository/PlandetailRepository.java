package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlandetailRepository extends JpaRepository<Tripplandetail, Tripplandetail.TripplandetailId> {
    List<Tripplandetail> findByPlannum(String plannum);
    Optional<Tripplandetail> findByPlannumAndTripdateAndDestination(String plannum, int tripdate, String destination);
    void deleteByPlannum(String plannum);
    void deleteByPlannumAndTripdateAndDestination(String plannum, int tripdate, String destination);
}