package com.kumoh.tour.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "T_TOUR")
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T_KEY", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_KEY")
    private MemberEntity member;

    @OneToMany(mappedBy = "tour")
    private final List<TourJoinEntity> tourJoins = new ArrayList<>();

    @Column(name = "T_TITLE", nullable = false)
    private String title;
    @Column(name = "T_CONTENTS", nullable = false)
    private String contents;

    @Column(name = "T_DEPARTURE_ADDR", nullable = false)
    private String departureAddr;
    @Column(name = "T_DEPARTURE_LAT", nullable = false)
    private String departureLat;
    @Column(name = "T_DEPARTURE_LNG", nullable = false)
    private String departureLng;

    @Column(name = "T_DESTINATION_ADDR", nullable = false)
    private String destinationAddr;
    @Column(name = "T_DESTINATION_LAT", nullable = false)
    private String destinationLat;
    @Column(name = "T_DESTINATION_LNG", nullable = false)
    private String destinationLng;

    @Column(name = "T_ACTIVITY")
    private String activity;
    @Column(name = "T_START_AGE", nullable = false)
    private int startAge;
    @Column(name = "T_END_AGE", nullable = false)
    private int endAge;

    @Column(name = "T_SEX", nullable = false)
    private int sex;
    @Column(name = "T_CAPACITY", nullable = false)
    private int capacity;

    @Column(name = "T_START_DATE", nullable = false)
    private String startDate;

    @Column(name = "T_END_DATE", nullable = false)
    private String endDate;

    @Column(name = "T_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private TourStatus status;

    public void setMember(MemberEntity member) {
        this.member = member;
        if (!member.getTours().contains(this)) {
            member.getTours().add(this);
        }
    }

    public void addTourJoin(TourJoinEntity tourJoin) {
        tourJoins.add(tourJoin);
        if (tourJoin.getTour() != this) {
            tourJoin.setTour(this);
        }
    }
}
