package com.TripPlan.TripPlanProject.controller;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.User;
import com.TripPlan.TripPlanProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(new UserResponseDTO("Success", "회원가입이 성공적으로 완료되었습니다."), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UserResponseDTO("Fail", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-id")
    public ResponseEntity<?> checkIdUser(@RequestParam String userId) {
        try {
            userService.validateUserId(userId);
            return new ResponseEntity<>(new UserResponseDTO("Success", "사용 가능한 아이디입니다."), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UserResponseDTO("Fail", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailUser(@RequestParam String email) {
        try {
            userService.validateEmail(email);
            return new ResponseEntity<>(new UserResponseDTO("Success", "사용 가능한 이메일입니다."), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UserResponseDTO("Fail", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
