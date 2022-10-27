package com.kumoh.tour.vo;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourSearch {
    private String title;
    private String activity;
    private int sex;
    private int startAge;
    private int endAge;
    private String startDate;
    private String endDate;
    private String departureAddr;
    private String destinationAddr;
    private String departureLat;
    private String departureLng;
    private String destinationLat;
    private String destinationLng;
}
