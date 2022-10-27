package com.kumoh.tour.controller;

import com.kumoh.tour.dto.MemberDTO;
import com.kumoh.tour.dto.ResponseDTO;
import com.kumoh.tour.dto.TourDTO;
import com.kumoh.tour.entity.TourEntity;
import com.kumoh.tour.entity.TourStatus;
import com.kumoh.tour.service.TourService;
import com.kumoh.tour.vo.TourSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tour")
@Slf4j
public class TourController {
    private TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody TourDTO dto) {
        /* POST localhost:8080/tour
         *   {
         *      "memberId" : 1,
         *      "title" : "제주여행",
         *      "contents" : "한라산 탐방과 서핑을 목적으로 하는 여행입니다.",
         *      "departureAddr" : "포항시 북구",
         *      "departureLat" : 36.1071,
         *      "departureLng" : 129.3385,
         *      "destinationAddr" : "제주시 한라산",
         *      "destinationLat" : 33.3627,
         *      "destinationLng" : 126.5299,
         *      "activity" : "등산, 서핑",
         *      "startAge" : 20,
         *      "endAge" : 30,
         *      "sex" : 2,
         *      "capacity" : 5,
         *      "startDate" : "2022-10-05",
         *      "endDate" : "2022-10-10"
         *  }
         * */

        ResponseDTO<TourDTO> response = null;
        try {
            dto.setStatus(TourStatus.FINDING);
            TourDTO savedDTO = new TourDTO(tourService.save(TourDTO.toEntity(dto), dto.getMemberId()));
            response = ResponseDTO.<TourDTO>builder().data(savedDTO).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = ResponseDTO.<TourDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAllFindingStatus() {
        /*
         * GET localhost:8080/tour
         * return [tour1, tour2, ...]
         *
         * */
        List<TourEntity> entities = tourService.findAllFindingStatus();
        List<TourDTO> dtos = entities.stream().map(TourDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(TourSearch search) {
        /*
         * GET localhost:8080/tour/search?
         * title=xxx&activity=xxx&sex=1&startAge=20&endAge=30&
         * startDate=2022-10-01&endDate=2022-10-10
         *
         * */
        log.info("Search Params: {}", search);

        List<TourDTO> dtos = tourService.findBySearchParams(search).stream().map(TourDTO::new).collect(Collectors.toList());
        ResponseDTO<List<TourDTO>> response = ResponseDTO.<List<TourDTO>>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/latlng")
    public ResponseEntity<?> searchByLatLng(TourSearch search) {
        log.info("search: {}", search.toString());
        List<TourDTO> dtos = tourService.findByLatLng(search.getDepartureLat(),search.getDepartureLng()).stream().map(TourDTO::new).collect(Collectors.toList());
        ResponseDTO<List<TourDTO>> response = ResponseDTO.<List<TourDTO>>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable(required = true) Long id) {
        /*
         * GET localhost:8080/tour/detail/{id}
         * return {
         *      id: 1,
         *      memberId: 1,
         *      ...
         * }
         *
         * */

        TourDTO dto = new TourDTO(tourService.findById(id));
        ResponseDTO<TourDTO> response = ResponseDTO.<TourDTO>builder().data(dto).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/open/{memberId}")
    public ResponseEntity<?> findOpenTourByMemberId(@PathVariable(required = true) Long memberId) {
        /*
         * GET localhost:8080/tour/open/{memberId}
         * return [tour1, tour2, ...]
         *
         * */

        List<TourEntity> entities = tourService.findByMemberId(memberId);
        List<TourDTO> dtos = entities.stream().map(TourDTO::new).collect(Collectors.toList());

        ResponseDTO<List<TourDTO>> response = ResponseDTO.<List<TourDTO>>builder()
                .data(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/complete/{id}")
    public ResponseEntity<?> completeFinding(@PathVariable Long id) {
        /*
         * GET localhost:8080/complete/{id}
         *
         * */

        ResponseDTO<TourDTO> response = null;
        try {
            TourDTO dto = new TourDTO(tourService.completeFinding(id));
            response = ResponseDTO.<TourDTO>builder().data(dto).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = ResponseDTO.<TourDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/member/{tourId}")
    public ResponseEntity<?> findOpenMember(@PathVariable Long tourId) {
        ResponseDTO<MemberDTO> response = null;
        try {
            MemberDTO dto = new MemberDTO(tourService.findMemberByTourId(tourId));
            response = ResponseDTO.<MemberDTO>builder().data(dto).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = ResponseDTO.<MemberDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}