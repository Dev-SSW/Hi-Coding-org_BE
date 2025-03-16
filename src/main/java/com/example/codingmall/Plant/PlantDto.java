package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDto {
    private Long id;
    private Long userId;
    private String name; // 식물 이름
    private int idealTemperature;   //이상적인 온도
    private int idealHumidity;      //이상적인 습도
    private int idealSolidMoisture; //이상적인 토양습도
    private int idealLightIntensity;//이상적인 광량
    private int growthTarget;       //목표 성장 길이

    public static PlantDto from (Plant plant){
        return PlantDto.builder()
                .id(plant.getPlant_id())
                .userId(plant.getUser().getId())
                .name(plant.getName())
                .idealTemperature(plant.getIdealTemperature())
                .idealHumidity(plant.getIdealHumidity())
                .idealSolidMoisture(plant.getIdealSolidMoisture())
                .idealLightIntensity(plant.getIdealLightIntensity())
                .growthTarget(plant.getGrowthTarget())
                .build();
    }


    public Plant toEntity(User user){
        return Plant.builder()
                .plant_id(this.getId())
                .user(user)
                .name(this.name)
                .idealTemperature(this.getIdealTemperature())
                .idealHumidity(this.idealHumidity)
                .idealSolidMoisture(this.idealSolidMoisture)
                .idealLightIntensity(this.idealLightIntensity)
                .growthTarget(this.getGrowthTarget())
                .build();
    }
}
