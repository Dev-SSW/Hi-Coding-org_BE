package com.example.codingmall.PlantRecommand;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MBTIResponseDto {
    private int E,I,N,S,T,F,P,J; // 응답한 MBTI 갯수
}
