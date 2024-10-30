package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.TripMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripMemberRepository extends JpaRepository<TripMember, Long> {
    Optional<TripMember> findByPlannumAndUserId(String plannum, String userId);
    void deleteByPlannumAndUserId(String plannum, String userIdfordelete);
    List<TripMember> findByPlannum(String plannum);

    @Query("SELECT t.plannum FROM TripMember t WHERE t.userId = :userId")
    List<String> findPlannumsByUserId(@Param("userId") String userId);
}