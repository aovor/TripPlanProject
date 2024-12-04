package com.TripPlan.TripPlanProject.UserPackage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "userdata")
public class User {
    @Id
    @Column(name = "UserID", length = 50)
    private String userId; // 사용자 ID (기본 키)

    @Column(name = "Name", length = 50, nullable = false)
    private String name; // 사용자 성명

    @Column(name = "Nickname", length = 50, nullable = false)
    private String nickname; // 별명

    @Column(name = "Password", length = 100, nullable = false)
    private String password; // 비밀번호

    @Column(name = "Email", length = 50, nullable = false, unique = true)
    private String email; // 이메일

    @Column(name = "Joindate", nullable = false)
    private LocalDateTime joinDate = LocalDateTime.now(); // 가입 날짜

    public User() {}

    @PrePersist
    public void prePersist() {
        this.joinDate = LocalDateTime.now(); // 가입 날짜 자동 설정
    }
}
