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

    private int growth;             //성장 길이
    private String content;         //기록할 내용
    private LocalDate record;       //기록된 시간

    /* 생성 메서드 */
    public static PlantGrowthLog createLog(Plant plant, PlantGrowthLogRequest plantGrowthLogRequest) {
        return PlantGrowthLog.builder()
                .plant(plant)
                .growth(plantGrowthLogRequest.getGrowth())
                .content(plantGrowthLogRequest.getContent())
                .record(plantGrowthLogRequest.getRecord())
                .build();
    }

    /* 수정 메서드 */
    public void updateLog(int updateGrowth, String updateContent , LocalDate updateRecord) {
        this.growth = updateGrowth;
        this.content = updateContent;
        this.record = updateRecord;
    }
}
