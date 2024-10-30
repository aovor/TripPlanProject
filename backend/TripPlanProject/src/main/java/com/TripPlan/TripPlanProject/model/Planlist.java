package com.TripPlan.TripPlanProject.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Planlist")
@Getter
@Setter
@NoArgsConstructor
public class Planlist {
    @Id
    @Column(name = "Plannum", nullable = false, length = 20)
    private String plannum;  // 중복 불가능, 생성 날짜+숫자 무작위 (ex: 24092612345)

    @Column(name = "UserID", nullable = false, length = 50)
    private String userId;  // 여행 일정 생성한 사용자의 ID

    @Column(name = "Startdate", nullable = false, length = 20)
    private String startDate;  // 여행 시작 날짜 (ex: 2024-09-26)

    @Column(name = "Enddate", nullable = false, length = 20)
    private String endDate;  // 여행 종료 날짜 (ex: 2024-09-28)

    @Column(name = "Triptotaldate", nullable = false)
    private int tripTotalDate;  // 총 여행 일수 (ex: 3)

    @Column(name = "Tripregion", nullable = false, length = 30)
    private String tripRegion;  // 여행 지역 (ex: 제주)

    @Column(name = "Triproute", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String tripRoute;  // 배열 형태로 저장, (ex: [1일차: {1: "A", 2: "B", 3: "C", 4: "D", 5: "E"}])

    @Column(name = "Tripopen", nullable = false)
    private boolean tripOpen;  // 1: 공개, 0: 비공개

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "planlist", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TripMember> tripMembers;

    @OneToMany(mappedBy = "planlist", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Tripplandetail> tripplandetails;
}
