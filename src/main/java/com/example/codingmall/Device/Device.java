package com.example.codingmall.Device;

import com.example.codingmall.Enviorment.Environment;
import com.example.codingmall.User.User;
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
public class Device {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id")
    private Long deviceId;

    private final LocalDateTime registerDate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enviorment_id",nullable = false)
    private Environment environment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

}
