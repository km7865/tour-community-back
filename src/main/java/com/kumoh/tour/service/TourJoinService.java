package com.kumoh.tour.service;

import com.kumoh.tour.entity.JoinStatus;
import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.entity.TourEntity;
import com.kumoh.tour.entity.TourJoinEntity;
import com.kumoh.tour.persistence.MemberRepository;
import com.kumoh.tour.persistence.TourJoinRepository;
import com.kumoh.tour.persistence.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TourJoinService {
    private MemberRepository memberRepository;
    private TourRepository tourRepository;
    private TourJoinRepository tourJoinRepository;

    public TourJoinService(
            MemberRepository memberRepository,
            TourRepository tourRepository,
            TourJoinRepository tourJoinRepository) {
        this.memberRepository = memberRepository;
        this.tourRepository = tourRepository;
        this.tourJoinRepository = tourJoinRepository;
    }

    public TourJoinEntity save(Long tourId, Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElse(null);
        TourEntity tour = tourRepository.findById(tourId).orElse(null);

        if (member == null) {
            throw new RuntimeException("Member doesn't exists.");
        }

        if (tour == null) {
            throw new RuntimeException("Tour doesn't exists.");
        }

        log.info("Member's id: {}", member.getId());
        log.info("Tour opener's id: {}", tour.getMember().getId());

        if (member.getId() == tour.getMember().getId()) {
            throw new RuntimeException("Opener can't join.");
        }

        //int age = member.getBirth() + 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        int birth = Integer.parseInt(member.getBirth().substring(0, 4));
        int year = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
        int age = year - birth + 1;

        if (age < tour.getStartAge() || age > tour.getEndAge()) {
            throw new RuntimeException("Age doesn't match.");
        }

        // 이미 참여한 여행에 중복으로 참여할 수 없음
        if (tourJoinRepository.existsByTourIdAndMemberId(tourId, memberId)) {
            throw new RuntimeException("You already joined.");
        }

        // 최대인원: tour 에 존재
        // 현재인원: tourId 기반 참여여행 조회 count + 1(개설자)
        int maximum = tour.getCapacity();
        int current = tourJoinRepository.countByTourId(tourId, JoinStatus.JOIN) + 1;

        if (current == maximum) {
            throw new RuntimeException("Tour is full.");
        }

        TourJoinEntity entity = TourJoinEntity.builder().tour(tour).member(member).build();
        entity.setStatus(JoinStatus.JOIN);

        TourJoinEntity saved = tourJoinRepository.save(entity);

        return saved;
    }

    public List<TourJoinEntity> findByMemberId(Long memberId) {
        return tourJoinRepository.findByMemberId(memberId);
    }

    public int countByTourId(Long tourId) {
        return tourJoinRepository.countByTourId(tourId, JoinStatus.JOIN) + 1;
    }

    public List<MemberEntity> findMemberByTourId(Long tourId) {
        return tourJoinRepository.findMemberByTourId(tourId);
    }
}
