package com.example.codingmall.Enviorment;

import com.example.codingmall.Device.Device;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
public class Environment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "environment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private int temperature;            //온도
    private int humidity;               //습도
    private int soloidMoisture;         //토양 습도
    private int lightIntensity;         //광량
    private LocalDateTime recorededAt;  //기록된 시간

}
