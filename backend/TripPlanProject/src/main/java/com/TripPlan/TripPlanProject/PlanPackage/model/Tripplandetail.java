package com.TripPlan.TripPlanProject.PlanPackage.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tripplandetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(Tripplandetail.TripplandetailId.class) // 복합 키를 위한 @IdClass 추가
public class Tripplandetail {
    @Id
    @Column(name = "Plannum", nullable = false, length = 20)
    private String plannum;

    @Id
    @Column(name = "Tripdate", nullable = false)
    private int tripdate;

    @Id
    @Column(name = "Destination", nullable = false, length = 30)
    private String destination;

    @Column(name = "Vector", nullable = false, length = 100)
    private String vector;

    @Column(name = "Visit", nullable = false)
    private int visit;

    @Column(name = "Memo", length = 500)
    private String memo;

    @ManyToOne
    @JoinColumn(name = "Plannum", referencedColumnName = "Plannum", insertable = false, updatable = false)
    private Planlist planlist;

    // 복합 키를 정의하기 위한 내부 클래스
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TripplandetailId implements Serializable {
        private String plannum;
        private int tripdate;
        private String destination;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TripplandetailId that = (TripplandetailId) o;
            return tripdate == that.tripdate && plannum.equals(that.plannum) && destination.equals(that.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(plannum, tripdate, destination);
        }
    }
}
