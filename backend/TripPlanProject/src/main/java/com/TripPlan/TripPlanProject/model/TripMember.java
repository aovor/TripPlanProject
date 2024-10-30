package com.TripPlan.TripPlanProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tripmember")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(TripMember.TripMemberId.class)  // 복합 키 설정을 위한 IdClass 사용
public class TripMember {

    @Id
    @Column(name = "Plannum", nullable = false, length = 20)
    private String plannum;

    @Id
    @Column(name = "UserID", nullable = false, length = 50)
    private String userId;

    @Column(name = "Authority", nullable = false)
    private int authority;

    @ManyToOne
    @JoinColumn(name = "Plannum", referencedColumnName = "Plannum", insertable = false, updatable = false)
    private Planlist planlist;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private User user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TripMemberId implements Serializable {
        private String plannum;
        private String userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TripMemberId that = (TripMemberId) o;
            return Objects.equals(plannum, that.plannum) &&
                    Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(plannum, userId);
        }
    }
}
