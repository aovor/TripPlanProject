package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.model.Planlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanlistRepository extends JpaRepository<Planlist, Long> {
    List<Planlist> findByUserId(String userId);
    boolean existsByPlannum(String plannum);
}