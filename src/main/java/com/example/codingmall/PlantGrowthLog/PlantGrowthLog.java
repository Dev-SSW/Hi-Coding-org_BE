package com.example.codingmall.PlantGrowthLog;

import com.example.codingmall.Plant.Plant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlantGrowthLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantGrowthLog_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id" )
    private Plant plant;

    private int growth;             //현재 길이
    private int percentage;         //목표 대비 성장률
    private LocalDateTime record;   //기록된 시간
}
