package com.example.codingmall.PlantGrowthLog;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlantGrowthLogResponse {
    private Long plantId;
    private LocalDate date;

    private Integer growth;
    private String content;

    private int totalGrowth;
    private int percentage;
}
