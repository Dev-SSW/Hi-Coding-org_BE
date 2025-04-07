package com.example.codingmall.PlantRecommand;

import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// 성격에 맞는 식물 추천
public class PlantRecommand {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantRecommand_id")
    private Long id;

    private String MBTIResult; //MBTI 검사 결과
    private String plantRecommandResult;  // MBTI 검사 결과에 따른 추천 식물 결과
    private Timestamp resultAt; // 결과 기록 날짜
}
