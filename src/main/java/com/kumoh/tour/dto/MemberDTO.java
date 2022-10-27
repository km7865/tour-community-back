package com.kumoh.tour.dto;

import com.kumoh.tour.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private String token;
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private boolean sex;
    private String birth;
    private String area;

    public MemberDTO(MemberEntity entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.sex = entity.isSex();
        this.birth = entity.getBirth();
        this.area = entity.getArea();
    }

    public static MemberEntity toEntity(MemberDTO dto) {
        return MemberEntity.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .sex(dto.isSex())
                .birth(dto.getBirth())
                .area(dto.getArea())
                .build();
    }
}
