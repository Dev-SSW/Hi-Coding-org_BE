package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name =  "Plant" , description = "나의 식물 만들기 / 수정 / 기록 API")
@RestController
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;
    @GetMapping("plants/getList")
    @Operation(summary = "나의 식물 목록 조회" , description = "현재 내가 등록한 식물을 리스트 형태로 조회합니다.")
    public ResponseEntity<List<PlantDto>> getAllPlants(@AuthenticationPrincipal User user){
        List<PlantDto> allPlants = plantService.findAllPlants(user);
        return ResponseEntity.ok(allPlants);
    }

    @PostMapping("Plant/create")
    @Operation(summary = "식물 등록하기" , description = "내가 새로운 식물을 등록합니다.")
    public ResponseEntity<Plant> createPlant(@AuthenticationPrincipal User user, @RequestBody PlantDto plantDto){
        Plant plant = plantService.createPlant(plantDto, user);
        return ResponseEntity.ok(plant);
    }

    @PutMapping("Plant/update/{PlantId}")
    @Operation(summary = "식물등록 수정하기", description = "등록한 식물을 수정합니다")
    public ResponseEntity<PlantDto> updatePlang(@AuthenticationPrincipal User user , @RequestBody PlantDto plantDto){
        PlantDto updatePlant = PlantDto.from(plantService.updatePlant(plantDto,user));
        return ResponseEntity.ok(updatePlant);
    }
}