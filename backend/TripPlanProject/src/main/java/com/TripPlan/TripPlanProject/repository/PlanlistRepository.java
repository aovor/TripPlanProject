package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlanlistRepository extends JpaRepository<Planlist, Long> {
    List<Planlist> findByUserId(String userId);
    boolean existsByPlannum(String plannum);
    Optional<Planlist> findByPlannum(String plannum);
    boolean existsByPlannumAndUserId(String plannum, String userId);
    Optional<Planlist> findByPlannumAndUserId(String plannum, String userId);

    void deleteByPlannum(String plannum);
}