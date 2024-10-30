package com.TripPlan.TripPlanProject.service;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.TripMember;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import com.TripPlan.TripPlanProject.repository.PlandetailRepository;
import com.TripPlan.TripPlanProject.repository.PlanlistRepository;
import com.TripPlan.TripPlanProject.repository.TripMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PlanlistService {
    private final PlanlistRepository planlistRepository;
    private final PlandetailRepository plandetailRepository;
    private final TripMemberRepository tripMemberRepository;

    public PlanlistService(PlanlistRepository planlistRepository, PlandetailRepository plandetailRepository, TripMemberRepository tripMemberRepository){
        this.planlistRepository = planlistRepository;
        this.plandetailRepository = plandetailRepository;
        this.tripMemberRepository = tripMemberRepository;
    }

    // 여행 일정 생성
    public UserResponseDTO createPlan(Planlist trip) {
        String plannum = generateUniquePlannum();
        trip.setPlannum(plannum);
        planlistRepository.save(trip);

        TripMember tripMember = new TripMember();
        tripMember.setPlannum(plannum);
        tripMember.setUserId(trip.getUserId());
        tripMember.setAuthority(1);
        tripMemberRepository.save(tripMember);

        return new UserResponseDTO("Success", "일정이 성공적으로 추가되었습니다.");
    }

    // 여행 세부 일정 생성
    public UserResponseDTO addDetail(Tripplandetail trip, String userId) {
        if (!hasAuthority(trip.getPlannum(), userId, 1, 2)) {
            return new UserResponseDTO("Fail", "일정을 생성할 권한이 없습니다.");
        }

        plandetailRepository.save(trip);
        return new UserResponseDTO("Success", "일정이 성공적으로 추가되었습니다.");
    }

    // 여행 일정 수정
    public UserResponseDTO updatePlan(Planlist updatePlan, String plannum, String userId) {
        if (!hasAuthority(plannum, userId, 1, 2)) {
            return new UserResponseDTO("Fail", "일정을 수정할 권한이 없습니다.");
        }

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
    public UserResponseDTO updatePlanDetail(Tripplandetail updateDetail, String plannum, String userId, int tripdate, String Destination) {
        if (!hasAuthority(plannum, userId, 1, 2)) {
            return new UserResponseDTO("Fail", "일정을 수정할 권한이 없습니다.");
        }

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
    public UserResponseDTO deletePlan(String plannum, String userId) {
        if (!hasAuthority(plannum, userId, 1)) {
            return new UserResponseDTO("Fail", "일정을 삭제할 권한이 없습니다.");
        }
        Planlist planlist = planlistRepository.findByPlannum(plannum)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        planlistRepository.delete(planlist);

        return new UserResponseDTO("Success", "일정이 성공적으로 삭제되었습니다.");
    }

    // 여행 세부 일정 삭제
    @Transactional
    public UserResponseDTO deletePlanDetail(String plannum, String userId, int tripdate, String destination) {
        if (!hasAuthority(plannum, userId, 1, 2)) {
            return new UserResponseDTO("Fail", "일정을 삭제할 권한이 없습니다.");
        }

        plandetailRepository.deleteByPlannumAndTripdateAndDestination(plannum, tripdate, destination);
        return new UserResponseDTO("Success", "세부 일정이 성공적으로 삭제되었습니다.");
    }

    // 여행 일정 리스트 반환
    public List<Planlist> getPlanlistsByUserId(String userId) {
        List<String> plannums = tripMemberRepository.findPlannumsByUserId(userId);
        return planlistRepository.findByPlannumIn(plannums);
    }

    // 여행 세부 일정 리스트 반환
    public List<Tripplandetail> getPlandetailByPlannum(String plannum) {
        return plandetailRepository.findByPlannum(plannum);
    }

    // 여행 동행인 추가
    public UserResponseDTO addTripMember(String plannum, String userId, String userIdforadd, int authority) {
        if (!hasAuthority(plannum, userId, 1)) {
            return new UserResponseDTO("Fail", "동행인을 추가할 권한이 없습니다.");
        }

        TripMember addtripMember = new TripMember();
        addtripMember.setPlannum(plannum);
        addtripMember.setUserId(userIdforadd);
        addtripMember.setAuthority(authority);
        tripMemberRepository.save(addtripMember);

        return new UserResponseDTO("Success", "동행인 추가가 성공적으로 완료되었습니다.");
    }

    // 여행 동행인 조회
    public List<TripMember> listTripMember(String plannum) {
        return tripMemberRepository.findByPlannum(plannum);
    }

    // 여행 동행인 삭제
    @Transactional
    public UserResponseDTO deleteTripMember(String plannum, String userId, String userIdfordelete) {
        if (!hasAuthority(plannum, userId, 1)) {
            return new UserResponseDTO("Fail", "동행인을 삭제할 권한이 없습니다.");
        }

        tripMemberRepository.deleteByPlannumAndUserId(plannum, userIdfordelete);
        return new UserResponseDTO("Success", "동행인 삭제가 성공적으로 완료되었습니다.");
    }

    // 권한 검사
    private boolean hasAuthority(String plannum, String userId, int... requiredAuthorities) {
        Optional<TripMember> tripMemberOpt = tripMemberRepository.findByPlannumAndUserId(plannum, userId);
        if (tripMemberOpt.isEmpty()) {
            return false;
        }

        int userAuthority = tripMemberOpt.get().getAuthority();
        for (int requiredAuthority : requiredAuthorities) {
            if (userAuthority == requiredAuthority) {
                return true;
            }
        }
        return false;
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
