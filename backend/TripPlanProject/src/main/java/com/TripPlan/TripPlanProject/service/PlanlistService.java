package com.TripPlan.TripPlanProject.service;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import com.TripPlan.TripPlanProject.repository.PlandetailRepository;
import com.TripPlan.TripPlanProject.repository.PlanlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PlanlistService {
    @Autowired
    private final PlanlistRepository planlistRepository;
    private final PlandetailRepository plandetailRepository;

    public PlanlistService(PlanlistRepository planlistRepository, PlandetailRepository plandetailRepository){
        this.planlistRepository = planlistRepository;
        this.plandetailRepository = plandetailRepository;
    }

    // 여행 일정 생성
    public UserResponseDTO createPlan(Planlist trip) {
        String plannum = generateUniquePlannum();
        trip.setPlannum(plannum);
        planlistRepository.save(trip);
        return new UserResponseDTO("Success", "일정이 성공적으로 추가되었습니다.");
    }

    // 여행 세부 일정 생성
    public UserResponseDTO addDetail(Tripplandetail trip) {
        plandetailRepository.save(trip);
        return new UserResponseDTO("Success", "일정이 성공적으로 추가되었습니다.");
    }

    // 여행 일정 리스트
    public List<Planlist> getPlanlistsByUserId(String userId) {
        return planlistRepository.findByUserId(userId);
    }

    // 여행 세부 일정 리스트
    public List<Tripplandetail> getPlandetailByPlannum(String plannum) {
        return plandetailRepository.findByPlannum(plannum);
    }


    // plannum 생성
    private String generateUniquePlannum() {
        String plannum;
        boolean exists;

        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        do {
            int randomNumber = new Random().nextInt(100000);
            String randomStr = String.format("%05d", randomNumber);

            plannum = today + randomStr;

            exists = planlistRepository.existsByPlannum(plannum);
        } while (exists);

        return plannum;
    }
}
