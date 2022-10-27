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
@Table (name = "T_MEMBER")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "M_KEY")
    private Long id;

    @Column(name = "M_EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "M_PASSWORD", nullable = false)
    private String password;
    @Column(name = "M_NAME", nullable = false)
    private String name;
    @Column(name = "M_PHONE", nullable = false)
    private String phone;
    @Column(name = "M_SEX", nullable = false)
    private boolean sex;
    @Column(name = "M_BIRTH", nullable = false)
    private String birth;
    @Column(name = "M_AREA", nullable = false)
    private String area;

    @OneToMany(mappedBy = "member")
    private final List<TourEntity> tours = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<TourJoinEntity> tourJoins = new ArrayList<>();

    public void addTourEntity(TourEntity tour) {
        this.tours.add(tour);
        if (tour.getMember() != this) {
            tour.setMember(this);
        }
    }

    public void addTourJoinEntity(TourJoinEntity tourJoin) {
        this.tourJoins.add(tourJoin);
        if (tourJoin.getMember() != this) {
            tourJoin.setMember(this);
        }
    }
}
