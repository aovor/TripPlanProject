package com.TripPlan.TripPlanProject.service;

import com.TripPlan.TripPlanProject.dto.UserdetailResponseDTO;
import com.TripPlan.TripPlanProject.model.User;
import com.TripPlan.TripPlanProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";

    @Autowired
    private UserRepository userRepository;

    // 아이디 중복 + 글자수 확인
    public void validateUserId(String userId) {
        if (userId.length() < 5) {
            throw new IllegalArgumentException("아이디는 다섯 글자 이상이어야 합니다.");
        }

        if (userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("아이디가 이미 존재합니다.");
        }
    }

    // 이메일 중복 확인
    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이메일이 이미 존재합니다.");
        }
    }

    // 비밀번호 유효성 검사
    public void validatePassword(String password) {
        if (!password.matches(PASSWORD_REGEX) || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 대문자/소문자/특수문자를 포함하여 여덟 글자 이상이어야 합니다.");
        }
    }

    // 회원 가입
    public User registerUser(User user) {
        // 아이디와 이메일, 비밀번호 유효성 검사
        validateUserId(user.getUserId());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());

        // 비밀번호 암호화 후 저장
        user.setPassword(hashPassword(user.getPassword()));
        user.setJoinDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    // 본인 정보 조회
    public UserdetailResponseDTO getUserDetails(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserdetailResponseDTO responseDTO = new UserdetailResponseDTO();
            responseDTO.setUserId(user.getUserId());
            responseDTO.setName(user.getName());
            responseDTO.setNickname(user.getNickname());
            responseDTO.setEmail(user.getEmail());
            return responseDTO;
        } else {
            return null;
        }
    }

    // 비밀번호 해싱
    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
