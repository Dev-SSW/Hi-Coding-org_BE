package com.example.codingmall.Environment;

import com.example.codingmall.Device.Device;
import lombok.*;

@Getter
public class EnvironmentRequest {
    private Long deviceId;
    private int temperature;            //온도
    private int humidity;               //습도
    private int soloidMoisture;         //토양 습도
    private int lightIntensity;         //광량
}

