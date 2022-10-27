package com.kumoh.tour.controller;

import com.kumoh.tour.dto.MemberDTO;
import com.kumoh.tour.dto.ResponseDTO;
import com.kumoh.tour.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody MemberDTO dto) {
        try {
            MemberDTO savedDTO = new MemberDTO(memberService.save(MemberDTO.toEntity(dto)));
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(savedDTO).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/join/{tourId}")
    public ResponseEntity<?> findJoinMember(@PathVariable Long tourId) {
        try {
            List<MemberDTO> dtos = memberService.findByTourId(tourId).stream().map(MemberDTO::new).collect(Collectors.toList());
            ResponseDTO<List<MemberDTO>> response = ResponseDTO.<List<MemberDTO>>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
