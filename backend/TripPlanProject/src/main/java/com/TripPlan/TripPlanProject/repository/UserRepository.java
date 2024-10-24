package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 회원가입
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);

    // 로그인
    User findByUserId(String userId);
}
