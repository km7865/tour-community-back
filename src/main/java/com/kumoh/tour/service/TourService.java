package com.kumoh.tour.service;

import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.entity.TourEntity;
import com.kumoh.tour.entity.TourStatus;
import com.kumoh.tour.persistence.MemberRepository;
import com.kumoh.tour.persistence.TourRepository;
import com.kumoh.tour.persistence.TourSpecification;
import com.kumoh.tour.vo.TourSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TourService {
    private MemberRepository memberRepository;
    private TourRepository tourRepository;

    public TourService(MemberRepository memberRepository, TourRepository tourRepository) {
        this.memberRepository = memberRepository;
        this.tourRepository = tourRepository;
    }

    public TourEntity save(TourEntity entity, Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            throw new RuntimeException("Member doesn't exists.");
        }

        entity.setMember(member);
        TourEntity saved = tourRepository.save(entity);
        saved.setMember(member);

        return saved;
    }

    public TourEntity findById(Long id) {
        return tourRepository.findById(id).orElse(null);
    }

    public List<TourEntity> findAllFindingStatus() {
        log.info(TourStatus.FINDING.name());
        return tourRepository.findByStatus(TourStatus.FINDING);
    }

    public List<TourEntity> findByMemberId(Long memberId) {
        return tourRepository.findByMemberId(memberId);
    }

    public List<TourEntity> findByLatLng(String lat, String lng) {
        return tourRepository.findByLatLng(lat, lng);
    }

    public List<TourEntity> findBySearchParams(TourSearch search) {
        Map<String, Object> searchKeys = new HashMap<>();
        int departureDist = 10;
        int destinationDist = 10;

        if (search.getTitle() == null) search.setTitle("");
        if (search.getActivity() == null) search.setActivity("");
        if (search.getSex() == 0) search.setSex(3);
        if (search.getEndAge() == 0) search.setEndAge(99);
        if (search.getStartDate() == null) search.setStartDate(LocalDate.now().toString());
        //if (search.getStartDate() == null) search.setStartDate("2022-10-01");
        if (search.getEndDate() == null) search.setEndDate("2030-12-31");
        if (search.getDepartureLat() == null) {
            search.setDepartureLat("0");
            search.setDepartureLng("0");
            departureDist = Integer.MAX_VALUE;
        }
        if (search.getDestinationLat() == null) {
            search.setDestinationLat("0");
            search.setDestinationLng("0");
            destinationDist = Integer.MAX_VALUE;
        }
//        if (search.getTitle() != null) searchKeys.put("title", search.getTitle());
//        if (search.getActivity() != null) searchKeys.put("activity", search.getActivity());
//        if (search.getSex() != 0) searchKeys.put("setx", search.getSex());
//        if (search.getStartAge() != 0) searchKeys.put("startAge", search.getStartAge());
//        if (search.getEndAge() != 0) searchKeys.put("endAge", search.getEndAge());
//        if (search.getStartDate() != null) searchKeys.put("startDate", search.getStartDate());
//        if (search.getEndDate() != null) searchKeys.put("endDate", search.getEndDate());
//        //if (search.getDepartureAddr() != null) searchKeys.put("depar", search.getTitle());
//        if (search.getDepartureLat() != null) {
//            searchKeys.put("departureLat", search.getDepartureLat());
//            searchKeys.put("departureLng", search.getDepartureLng());
//        }
//        if (search.getDestinationLat() != null) {
//            searchKeys.put("destinationLat", search.getDestinationLat());
//            searchKeys.put("destinationLng", search.getDestinationLng());
//        }

        return tourRepository.findBySearchParams(
                search.getTitle(),
                search.getActivity(),
                search.getSex(),
                search.getStartAge(),
                search.getEndAge(),
                search.getStartDate(),
                search.getEndDate(),
                search.getDepartureLat(),
                search.getDepartureLng(),
                departureDist,
                search.getDestinationLat(),
                search.getDestinationLng(),
                destinationDist
        );
    }
    public TourEntity completeFinding(Long id) {
        TourEntity entity = tourRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new RuntimeException("Tour doesn't exists.");
        }

        if (entity.getStatus() != TourStatus.FINDING) {
            throw new RuntimeException("Tour's status is not 'FINDING'");
        }

        entity.setStatus(TourStatus.FOUND);
        tourRepository.save(entity);

        return entity;
    }

    public MemberEntity findMemberByTourId(Long tourId) {
        return tourRepository.findMemberByTourId(tourId);
    }
}
