package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User user;

    private String name;            //식물 이름
    private int idealTemperature;   //이상적인 온도
    private int idealHumidity;      //이상적인 습도
    private int idealSolidMoisture; //이상적인 토양습도
    private int idealLightIntensity;//이상적인 광량
    private int growthTarget;       //목표 성장 길이
    private int totalGrowth;        //전체 성장 길이
    private int percentage;         //목표 대비 성장률
    private String imageUrl;        //식물 이미지 URL

    public void updatePlant(PlantDto plantDto){
        this.name = plantDto.getName();
        this.idealTemperature = plantDto.getIdealTemperature();
        this.idealHumidity = plantDto.getIdealHumidity();
        this.idealSolidMoisture = plantDto.getIdealSolidMoisture();
        this.idealLightIntensity = plantDto.getIdealSolidMoisture();
        this.growthTarget = plantDto.getGrowthTarget();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /* 수정 메서드 */
    public void updateGrowth(int newTotalGrowth) {
        this.totalGrowth = newTotalGrowth;
        this.percentage = (int) ((double) newTotalGrowth / this.growthTarget * 100);
    }
}