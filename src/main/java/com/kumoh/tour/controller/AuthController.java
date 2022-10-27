package com.kumoh.tour.controller;

import com.kumoh.tour.dto.MemberDTO;
import com.kumoh.tour.dto.ResponseDTO;
import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.security.TokenProvider;
import com.kumoh.tour.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    // 로그인
    private MemberService memberService;
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(MemberService memberService,
                          TokenProvider tokenProvider) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO dto) {
        try {
            MemberEntity member = MemberEntity.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .birth(dto.getBirth())
                    .sex(dto.isSex())
                    .area(dto.getArea())
                    .build();

            log.info("Register member: {}", member.toString());

            MemberEntity saved = memberService.save(member);
            MemberDTO savedDto = new MemberDTO(saved);
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(savedDto).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> signin(@RequestBody MemberDTO dto) {
        MemberEntity member = memberService.getByCredentials(dto.getEmail(),
                dto.getPassword(), passwordEncoder);
        if (member != null) {
            final String token = tokenProvider.create(member);
            final MemberDTO signined = MemberDTO.builder()
                    .email(member.getEmail())
                    .id(member.getId())
                    .token(token)
                    .build();

            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(signined).build();

            return ResponseEntity.ok().body(response);
        } else {
            ResponseDTO response = ResponseDTO.builder().error("Login failed.").build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
