package com.kumoh.tour.service;

import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.persistence.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public MemberEntity save(MemberEntity entity) {
        if (entity == null || entity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        if (memberRepository.existsByEmail(entity.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        return memberRepository.save(entity);
    }

    public MemberEntity getByCredentials(final String email, final String password, PasswordEncoder passwordEncoder) {
        final MemberEntity originalMember = memberRepository.findByEmail(email);
        if (originalMember != null && passwordEncoder.matches(password, originalMember.getPassword())) {
            return originalMember;
        }

        return null;
    }

    public List<MemberEntity> findByTourId(Long tourId) {
        return memberRepository.findByTourId(tourId);
    }
}
