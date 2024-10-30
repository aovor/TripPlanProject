package com.TripPlan.TripPlanProject.controller;

import com.TripPlan.TripPlanProject.dto.DetailResponseDTO;
import com.TripPlan.TripPlanProject.dto.PlanlistResponseDTO;
import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import com.TripPlan.TripPlanProject.service.JwtTokenProvider;
import com.TripPlan.TripPlanProject.service.PlanlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanlistService planlistService;
    private final JwtTokenProvider jwtTokenProvider;

    public PlanController(PlanlistService planlistService, JwtTokenProvider jwtTokenProvider) {
        this.planlistService = planlistService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 일정 생성 api
    @PostMapping("/plancreate")
    public ResponseEntity<?> createPlan(@RequestHeader("Authorization") String token, @RequestBody PlanlistResponseDTO planRequest) {
        try{
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            planRequest.setUserId(userId);
            UserResponseDTO response = planlistService.createPlan(planRequest.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 세부 일정 생성 api
    @PostMapping("/plandetailcreate")
    public ResponseEntity<?> getCreateplandetail(@RequestHeader("Authorization") String token, @RequestBody DetailResponseDTO planRequest) {
        try{
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            if (planRequest.getPlannum() == null || planRequest.getPlannum().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plan number is missing");
            }

            UserResponseDTO response = planlistService.addDetail(planRequest.toEntity(), userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 일정 리스트 목록 확인 api
    @GetMapping("/planlist")
    public ResponseEntity<?> getPlanlists(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);

        if (!jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        try {
            String userId = jwtTokenProvider.getUsername(jwtToken);
            List<Planlist> planlists = planlistService.getPlanlistsByUserId(userId);
            if (planlists.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No planlists found for the userId");
            }

            List<PlanlistResponseDTO> response = planlists.stream()
                    .map(planlist -> new PlanlistResponseDTO(
                            planlist.getPlannum(),
                            planlist.getUserId(),
                            planlist.getStartDate(),
                            planlist.getEndDate(),
                            planlist.getTripTotalDate(),
                            planlist.getTripRegion(),
                            planlist.getTripRoute(),
                            planlist.isTripOpen()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
        }
    }

    // 세부 일정 목록 확인 api
    @GetMapping("/plandetaillist")
    public ResponseEntity<?> getPlanDetails(
            @RequestHeader("Authorization") String token,
            @RequestParam String plannum) {
        String jwtToken = token.substring(7);

        if (!jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        try {
            List<Tripplandetail> details = planlistService.getPlandetailByPlannum(plannum);
            if (details.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found for the provided plan number");
            }

            List<DetailResponseDTO> response = details.stream()
                    .map(detail -> new DetailResponseDTO(
                            detail.getPlannum(),
                            detail.getTripdate(),
                            detail.getDestination(),
                            detail.getVector(),
                            detail.getVisit(),
                            detail.getMemo()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // 일정 수정 api
    @PutMapping("/planupdate")
    public ResponseEntity<?> updatePlan(@RequestHeader("Authorization") String token,
                                        @RequestBody PlanlistResponseDTO planRequest,
                                        @RequestParam String plannum) {
        try {
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            UserResponseDTO response = planlistService.updatePlan(planRequest.toEntity(), plannum, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 세부 일정 수정 api
    @PutMapping("/plandetailupdate")
    public ResponseEntity<?> updatePlanDetail(@RequestHeader("Authorization") String token,
                                        @RequestBody DetailResponseDTO detailRequest,
                                              @RequestParam String plannum, int tripdate, String Destination) {
        try {
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            UserResponseDTO response = planlistService.updatePlanDetail(detailRequest.toEntity(), plannum, userId, tripdate, Destination);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 일정 삭제 api
    @DeleteMapping("/plandelete")
    public ResponseEntity<?> deletePlan(@RequestHeader("Authorization") String token,
                                        @RequestParam String plannum) {
        try {
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            UserResponseDTO response = planlistService.deletePlan(plannum, userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // 세부 일정 삭제 api
    @DeleteMapping("/plandetaildelete")
    public ResponseEntity<?> deleteDetailPlan(@RequestHeader("Authorization") String token,
                                        @RequestParam String plannum, int tripdate, String Destination) {
        try {
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            UserResponseDTO response = planlistService.deletePlanDetail(plannum, userId, tripdate, Destination);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // 여행 동행인 추가
    @PostMapping("/addtripmember")
    public ResponseEntity<?> addTripMember(@RequestHeader("Authorization") String token,
                                           @RequestParam String plannum, String userIdforadd, int authority) {
        try{
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            UserResponseDTO response = planlistService.addTripMember(plannum, userId, userIdforadd, authority);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 여행 동행인 삭제
    @DeleteMapping("/deletetripmember")
    public ResponseEntity<?> deleteTripMember(@RequestHeader("Authorization") String token,
                                              @RequestParam String plannum, String userIdfordelete) {
        try {
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            UserResponseDTO response = planlistService.deleteTripMember(plannum, userId, userIdfordelete);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
