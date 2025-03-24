package com.example.codingmall.PlantGrowthLog;

import lombok.Getter;

@Getter
public class PlantGrowthLogRequest {
    private int growth;             //현재 길이
    private String content;         //기록할 내용
}
