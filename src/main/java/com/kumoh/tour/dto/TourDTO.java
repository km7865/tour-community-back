package com.kumoh.tour.dto;

import com.kumoh.tour.entity.TourEntity;
import com.kumoh.tour.entity.TourStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDTO {
    private Long id;
    private Long memberId;
    private String title;
    private String contents;

    private String departureAddr;
    private double departureLat;
    private double departureLng;

    private String destinationAddr;
    private double destinationLat;
    private double destinationLng;

    private String activity;
    private int startAge;
    private int endAge;
    private int sex;
    private int capacity;
    private String startDate;
    private String endDate;
    private TourStatus status;

    public TourDTO(TourEntity entity) {
        this.id = entity.getId();
        this.memberId = entity.getMember().getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.departureAddr = entity.getDepartureAddr();
        this.departureLat = Double.parseDouble(entity.getDepartureLat());
        this.departureLng = Double.parseDouble(entity.getDepartureLng());
        this.destinationAddr = entity.getDestinationAddr();
        this.destinationLat = Double.parseDouble(entity.getDestinationLat());
        this.destinationLng = Double.parseDouble(entity.getDestinationLng());
        this.activity = entity.getActivity();
        this.startAge = entity.getStartAge();
        this.endAge = entity.getEndAge();
        this.sex = entity.getSex();
        this.capacity = entity.getCapacity();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.status = entity.getStatus();
    }

    public static TourEntity toEntity(TourDTO dto) {
        return TourEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .departureAddr(dto.getDepartureAddr())
                .departureLat(String.valueOf(dto.getDepartureLat()))
                .departureLng(String.valueOf(dto.getDepartureLng()))
                .destinationAddr(dto.getDestinationAddr())
                .destinationLat(String.valueOf(dto.getDestinationLat()))
                .destinationLng(String.valueOf(dto.getDestinationLng()))
                .activity(dto.getActivity())
                .startAge(dto.getStartAge())
                .endAge(dto.getEndAge())
                .sex(dto.getSex())
                .capacity(dto.getCapacity())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .build();
    }
}
