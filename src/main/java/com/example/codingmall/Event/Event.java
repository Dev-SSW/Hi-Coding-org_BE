package com.example.codingmall.Event;

import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Event")
public class Event {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 관리자 유저

    private String title;
    private String imageUrl;
    private String linkUrl;
    private LocalDateTime startDate; // 배너 표시 시작일
    private LocalDateTime endDate;
    private boolean isActivate; // 배너 활성화 여부
    private LocalDateTime createDate; // 배너 등록 날짜

}
