package com.TripPlan.TripPlanProject.controller;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.User;
import com.TripPlan.TripPlanProject.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDTO registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/check-id")
    public UserResponseDTO checkIdUser(@RequestParam String userId) {
        return userService.checkIdUser(userId);
    }

    @GetMapping("/check-email")
    public UserResponseDTO checkEmailUser(@RequestParam String email) {
        return userService.checkEmailUser(email);
    }
}
