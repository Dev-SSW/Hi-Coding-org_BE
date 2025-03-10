package com.example.codingmall.PlantRecommand;

import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlantRecommand {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantRecommand_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String Question; //질문
    private int Answer;      //답변
    private String Type;     //타입
    private int point;       //점수
}
