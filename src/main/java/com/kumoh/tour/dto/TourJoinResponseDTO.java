package com.kumoh.tour.dto;

import com.kumoh.tour.entity.JoinStatus;
import com.kumoh.tour.entity.TourJoinEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourJoinResponseDTO {
    private Long id;
    private TourDTO tour;
    private MemberDTO member;

    private JoinStatus status;

    public TourJoinResponseDTO(TourJoinEntity entity) {
        this.id = entity.getId();
        this.tour = new TourDTO(entity.getTour());
        this.member = new MemberDTO(entity.getMember());
        this.status = entity.getStatus();
    }
}
