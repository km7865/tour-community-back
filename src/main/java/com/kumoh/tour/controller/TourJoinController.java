package com.kumoh.tour.controller;


import com.kumoh.tour.dto.MemberDTO;
import com.kumoh.tour.dto.ResponseDTO;
import com.kumoh.tour.dto.TourJoinRequestDTO;
import com.kumoh.tour.dto.TourJoinResponseDTO;
import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.entity.TourJoinEntity;
import com.kumoh.tour.service.TourJoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tourjoin")
@Slf4j
public class TourJoinController {
    private TourJoinService tourJoinService;

    public TourJoinController(TourJoinService tourJoinService) {
        this.tourJoinService = tourJoinService;
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody TourJoinRequestDTO requestDTO) {
        ResponseDTO<TourJoinResponseDTO> response = null;
        try {
            TourJoinEntity saved = tourJoinService.save(requestDTO.getTourId(), requestDTO.getMemberId());
            TourJoinResponseDTO dto = new TourJoinResponseDTO(saved);
            response = ResponseDTO.<TourJoinResponseDTO>builder().data(dto).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = ResponseDTO.<TourJoinResponseDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> findByMemberId(@PathVariable Long memberId) {
        List<TourJoinEntity> entities = tourJoinService.findByMemberId(memberId);
        List<TourJoinResponseDTO> dtos = entities.stream().map(TourJoinResponseDTO::new).collect(Collectors.toList());
        ResponseDTO<List<TourJoinResponseDTO>> response = ResponseDTO.<List<TourJoinResponseDTO>>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/count/{tourId}")
    public ResponseEntity<?> countByTourId(@PathVariable Long tourId) {
        int cnt = tourJoinService.countByTourId(tourId);

        ResponseDTO<Integer> response = ResponseDTO.<Integer>builder().data(cnt).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/member/{tourId}")
    public ResponseEntity<?> findMemberByTourId(@PathVariable Long tourId) {
        List<MemberDTO> dtos = tourJoinService.findMemberByTourId(tourId).stream().map(MemberDTO::new).collect(Collectors.toList());

        ResponseDTO<List<MemberDTO>> response = ResponseDTO.<List<MemberDTO>>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
}
