package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Plant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;            //식물 이름
    private int idealTemperature;   //이상적인 온도
    private int idealHumidity;      //이상적인 습도
    private int idealSolidMoisture; //이상적인 토양습도
    private int idealLightIntensity;//이상적인 광량
    private int growthTarget;       //목표 성장 길이
    private int totalGrowth;        //전체 성장 길이

    public void updatePlant(PlantDto plantDto){
        this.name = plantDto.getName();
        this.idealTemperature = plantDto.getIdealTemperature();
        this.idealHumidity = plantDto.getIdealHumidity();
        this.idealSolidMoisture = plantDto.getIdealSolidMoisture();
        this.idealLightIntensity = plantDto.getIdealSolidMoisture();
        this.growthTarget = plantDto.getGrowthTarget();
    }

    public void setTotalGrowth(int growth) {
        totalGrowth = growth;
    }
}
