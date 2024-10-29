package com.TripPlan.TripPlanProject.service;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import com.TripPlan.TripPlanProject.repository.PlandetailRepository;
import com.TripPlan.TripPlanProject.repository.PlanlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 여행 일정 리스트 반환
    public List<Planlist> getPlanlistsByUserId(String userId) {
        return planlistRepository.findByUserId(userId);
    }

    // 여행 세부 일정 리스트 반환
    public List<Tripplandetail> getPlandetailByPlannum(String plannum) {
        return plandetailRepository.findByPlannum(plannum);
    }

    // 여행 일정 수정
    public UserResponseDTO updatePlan(Planlist updatePlan, String plannum) {
        Planlist existingPlan = planlistRepository.findByPlannum(plannum)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        existingPlan.setStartDate(updatePlan.getStartDate());
        existingPlan.setEndDate(updatePlan.getEndDate());
        existingPlan.setTripTotalDate(updatePlan.getTripTotalDate());
        existingPlan.setTripRegion(updatePlan.getTripRegion());
        existingPlan.setTripRoute(updatePlan.getTripRoute());
        existingPlan.setTripOpen(updatePlan.isTripOpen());

        planlistRepository.save(existingPlan);
        return new UserResponseDTO("Success", "일정이 성공적으로 수정되었습니다.");
    }

    // 여행 세부 일정 수정
    public UserResponseDTO updatePlanDetail(Tripplandetail updateDetail, String plannum, int tripdate, String Destination) {
        Tripplandetail existingDetail = plandetailRepository.findByPlannumAndTripdateAndDestination(
                plannum,
                tripdate,
                Destination
        ).orElseThrow(() -> new IllegalArgumentException("Plan detail not found"));

        existingDetail.setTripdate(updateDetail.getTripdate());
        existingDetail.setDestination(updateDetail.getDestination());
        existingDetail.setVector(updateDetail.getVector());
        existingDetail.setVisit(updateDetail.getVisit());
        existingDetail.setMemo(updateDetail.getMemo());

        plandetailRepository.save(existingDetail);
        return new UserResponseDTO("Success", "세부 일정이 성공적으로 수정되었습니다.");
    }

    // 여행 일정 삭제
    @Transactional
    public UserResponseDTO deletePlan(String plannum) {
        plandetailRepository.deleteByPlannum(plannum); // 만약 CascadeType.REMOVE를 사용하지 않는다면 이 부분이 필요합니다.
        planlistRepository.deleteByPlannum(plannum);

        return new UserResponseDTO("Success", "일정이 성공적으로 삭제되었습니다.");
    }

    // 여행 세부 일정 삭제
    @Transactional
    public UserResponseDTO deletePlanDetail(String plannum, int tripdate, String destination) {
        plandetailRepository.deleteByPlannumAndTripdateAndDestination(plannum, tripdate, destination);
        return new UserResponseDTO("Success", "세부 일정이 성공적으로 삭제되었습니다.");
    }

    // 소유자 확인 메서드
    public boolean isPlanOwner(String plannum, String userId) {
        return planlistRepository.existsByPlannumAndUserId(plannum, userId);
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
