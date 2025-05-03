package com.example.codingmall.PlantGrowthLog;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlantGrowthLogRequest {
    private int growth;             //현재 길이
    private String content;         //기록할 내용
    private LocalDate record;    //기록한 날짜
}
