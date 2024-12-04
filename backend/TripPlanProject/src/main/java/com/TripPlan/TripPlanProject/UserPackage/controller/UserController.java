package com.TripPlan.TripPlanProject.UserPackage.controller;

import com.TripPlan.TripPlanProject.config.JwtTokenProvider;
import com.TripPlan.TripPlanProject.UserPackage.model.User;
import com.TripPlan.TripPlanProject.UserPackage.service.UserService;
import com.TripPlan.TripPlanProject.UserPackage.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.UserPackage.dto.UserdetailResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원 가입 api
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(new UserResponseDTO("Success", "회원가입이 성공적으로 완료되었습니다."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UserResponseDTO("Fail", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 가입 시 아이디 중복 확인 api
    @GetMapping("/check-id")
    public ResponseEntity<?> checkIdUser(@RequestParam String userId) {
        try {
            userService.validateUserId(userId);
            return new ResponseEntity<>(new UserResponseDTO("Success", "사용 가능한 아이디입니다."), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UserResponseDTO("Fail", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 가입 시 이메일 중복 확인 api
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailUser(@RequestParam String email) {
        try {
            userService.validateEmail(email);
            return new ResponseEntity<>(new UserResponseDTO("Success", "사용 가능한 이메일입니다."), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UserResponseDTO("Fail", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // 본인 정보 조회 api
    @GetMapping("/userdetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);

        if (!jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        String userId = jwtTokenProvider.getUsername(jwtToken);

        try {
            UserdetailResponseDTO userDetail = userService.getUserDetails(userId);
            if (userDetail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for the given userId");
            }

            return ResponseEntity.ok(userDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
        }
    }
}
