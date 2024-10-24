package com.TripPlan.TripPlanProject.service;

import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.User;
import com.TripPlan.TripPlanProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserService {
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";

    @Autowired
    private UserRepository userRepository;

    // 아이디 중복 + 글자수 확인
    public UserResponseDTO checkIdUser(String userId) {
        if (userId.length() < 5){
            return new UserResponseDTO("Fail", "아이디는 다섯 글자 이상이어야 합니다.");
        }

        if (userRepository.existsByUserId(userId)) {
            return new UserResponseDTO("Fail", "아이디가 이미 존재합니다.");
        }
        return new UserResponseDTO("Success", "사용 가능한 아이디입니다.");
    }

    // 이메일 중복 확인
    public UserResponseDTO checkEmailUser(String email) {
        if (userRepository.existsByEmail(email)) {
            return new UserResponseDTO("Fail", "이메일이 이미 존재합니다.");
        }
        return new UserResponseDTO("Success", "사용 가능한 이메일입니다.");
    }

    // 회원 가입
    public UserResponseDTO registerUser(User user) {
        if (user.getUserId().length() < 5){
            return new UserResponseDTO("Fail", "아이디는 다섯 글자 이상이어야 합니다.");
        }

        if (userRepository.existsByUserId(user.getUserId())) {
            return new UserResponseDTO("Fail", "아이디가 이미 존재합니다.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return new UserResponseDTO("Fail", "이메일이 이미 존재합니다.");
        }

        if(!user.getPassword().matches(PASSWORD_REGEX)){
            return new UserResponseDTO("Fail", "비밀번호는 대문자/소문자/특수문자를 포함해야 합니다.");
        }

        user.setPassword(hashPassword(user.getPassword()));
        user.setJoinDate(LocalDateTime.now());
        userRepository.save(user);

        return new UserResponseDTO("Success", "회원가입이 성공적으로 완료되었습니다.");
    }

    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
