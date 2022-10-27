package com.kumoh.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourJoinRequestDTO {
    private Long id;
    private Long tourId;
    private Long memberId;

}
