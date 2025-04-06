package com.example.codingmall.Plant;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlantRequest {
    private String name;
    private int idealTemperature;
    private int idealHumidity;
    private int idealSolidMoisture;
    private int idealLightIntensity;
    private int growthTarget;
}
