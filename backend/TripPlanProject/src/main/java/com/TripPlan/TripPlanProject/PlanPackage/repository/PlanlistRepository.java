package com.TripPlan.TripPlanProject.PlanPackage.repository;

import com.TripPlan.TripPlanProject.PlanPackage.model.Planlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanlistRepository extends JpaRepository<Planlist, Long> {
    boolean existsByPlannum(String plannum);
    Optional<Planlist> findByPlannum(String plannum);


    @Query("SELECT p FROM Planlist p WHERE p.plannum IN :plannums")
    List<Planlist> findByPlannumIn(@Param("plannums") List<String> plannums);
}