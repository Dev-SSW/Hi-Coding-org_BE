package com.example.codingmall.PlantGrowthLog;

import com.example.codingmall.Plant.Plant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PlantGrowthLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantGrowthLog_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id" )
    @JsonIgnore //순환 참조 방지
    private Plant plant;

    private int growth;             //현재 길이
    private int sumGrowth;          //전체 성장 길이
    private int percentage;         //목표 대비 성장률
    private String content;         //기록할 내용
    private LocalDate record;   //기록된 시간

    /* 생성 메서드 */
    public static PlantGrowthLog createLog(Plant plant, PlantGrowthLogRequest plantGrowthLogRequest, int percentage, int sumGrowth) {
        return PlantGrowthLog.builder()
                .plant(plant)
                .growth(plantGrowthLogRequest.getGrowth())
                .sumGrowth(sumGrowth)
                .percentage(percentage)
                .content(plantGrowthLogRequest.getContent())
                .record(LocalDate.now())
                .build();
    }
}
