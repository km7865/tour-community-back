package com.kumoh.tour.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="T_TOUR_JOIN")
public class TourJoinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TJ_KEY", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_KEY")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_KEY")
    private TourEntity tour;

    @Enumerated(EnumType.STRING)
    @Column(name = "TJ_STATUS", nullable = false)
    private JoinStatus status;

    public void setMember(MemberEntity member) {
        this.member = member;
        if (!member.getTourJoins().contains(this)) {
            member.getTourJoins().add(this);
        }
    }

    public void setTour(TourEntity tour) {
        this.tour = tour;
        if (!tour.getTourJoins().contains(this)) {
            tour.getTourJoins().add(this);
        }
    }
}
