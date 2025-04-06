package com.example.codingmall.PlantRecommand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name =  "PlantRecommand" , description = "나의 성향에 맞는 식물 추천 API")
public class PlantRecommandController {
    private final PlantRecommandService plantRecommandService;
    @PostMapping("/public/getPlantRecommandResult")
    @Operation(summary = "MBTI 검사 결과 값을 가져와 추천 식물을 반환합니다.",
    responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 추천 식물을 반환하였습니다"),
            @ApiResponse(responseCode = "500" , description = "서버 내부 오류가 발생하였습니다.")
    })
    public ResponseEntity<String> getPlantResult(@RequestBody MBTIResponseDto mbtiResponseDto) {
        try {
            String plant = plantRecommandService.getPlantResult(mbtiResponseDto);
            return ResponseEntity.ok(plant);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("추천 식물 계산 중 오류가 발생하였습니다.");
        }
    }
}
