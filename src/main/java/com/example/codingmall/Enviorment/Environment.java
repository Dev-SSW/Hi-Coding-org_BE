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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Enviorment_id")
    private Long id;

    @JoinColumn(name = "device_id")
    @OneToOne
    private Device device;
    private int temperature;
    private int humidity;
    private int soloidMoisture;
    private int lightIntensity;
    private LocalDateTime recorededAt;

}
