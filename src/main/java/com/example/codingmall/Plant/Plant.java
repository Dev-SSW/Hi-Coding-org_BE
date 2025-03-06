package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Plant {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private int idealTemperature;
    private int idealHumidity;
    private int idealSolidMoisture;
    private int idealLightIntensity;
    private int growthTarget;
}
