package com.kumoh.tour.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum TourStatus {
    // 모집중, 모집완료, 진행중, 완료, 취소
    FINDING,
    FOUND,
    DOING,
    DONE,
    CANCEL;
}
