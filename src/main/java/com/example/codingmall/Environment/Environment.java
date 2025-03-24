package com.example.codingmall.Environment;

import com.example.codingmall.Device.Device;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Builder
public class Environment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "environment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    private int temperature;            //온도
    private int humidity;               //습도
    private int soloidMoisture;         //토양 습도
    private int lightIntensity;         //광량
    private LocalDateTime recorededAt;  //기록된 시간

    /* 생성 메서드 */
    public static Environment createEnvironment(Device device, EnvironmentRequest environmentRequest) {
        return Environment.builder()
                .device(device)
                .temperature(environmentRequest.getTemperature())
                .humidity(environmentRequest.getHumidity())
                .soloidMoisture(environmentRequest.getSoloidMoisture())
                .lightIntensity(environmentRequest.getLightIntensity())
                .recorededAt(LocalDateTime.now())
                .build();
    }
}
