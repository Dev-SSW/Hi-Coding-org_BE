package com.example.codingmall.Device;

import com.example.codingmall.Environment.Environment;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Device {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime registerDate; //등록일

    public static Device createDevice(User user) {
        return Device.builder()
                .user(user)
                .registerDate(LocalDateTime.now())
                .build();
    }
}
