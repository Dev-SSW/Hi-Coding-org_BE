package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name =  "Plant" , description = "나의 식물 만들기 / 수정 / 기록 API")
@RestController
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

    @GetMapping("/plant/getList")
    @Operation(summary = "나의 식물 목록 조회" , description = "현재 내가 등록한 식물을 리스트 형태로 조회합니다.")
    public ResponseEntity<List<PlantDto>> getAllPlants(@AuthenticationPrincipal User user){
        List<PlantDto> allPlants = plantService.findAllPlants(user);
        return ResponseEntity.ok(allPlants);
    }

    @GetMapping("/plant/getPlant/{plantId}")
    @Operation(summary = "개별 식물 조회", description = "plantId를 통하여 식물의 정보를 가져옵니다.")
    public ResponseEntity<Plant> getPlantInfo(@PathVariable(name = "plantId") Long plantId) {
        return ResponseEntity.ok(plantService.findPlantInfo(plantId));
    }

    @PutMapping(value = "/plant/update/{plantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "식물 등록 수정하기 (이미지 포함)", description = "등록한 식물을 수정합니다")
    public ResponseEntity<PlantDto> updatePlant(
            @AuthenticationPrincipal User user ,
            @PathVariable(value = "plantId") Long plantId,
            @ModelAttribute(value = "request") PlantRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws  IOException {
        PlantDto dto = PlantDto.builder()
                .id(plantId)
                .name(request.getName())
                .idealTemperature(request.getIdealTemperature())
                .idealHumidity(request.getIdealHumidity())
                .idealSolidMoisture(request.getIdealSolidMoisture())
                .idealLightIntensity(request.getIdealLightIntensity())
                .growthTarget(request.getGrowthTarget())
                .build();
        PlantDto updatePlant = PlantDto.from(plantService.updatePlant(dto, user, image));
        return ResponseEntity.ok(updatePlant);
    }

    @PostMapping(value = "/plant/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "식물 등록하기 (이미지 포함)", description = "식물 정보를 이미지와 함께 등록합니다.")
    public ResponseEntity<PlantDto> createPlant(
            @AuthenticationPrincipal User user,
            @ModelAttribute(value = "request") PlantRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        PlantDto dto = PlantDto.builder()
                .name(request.getName())
                .idealTemperature(request.getIdealTemperature())
                .idealHumidity(request.getIdealHumidity())
                .idealSolidMoisture(request.getIdealSolidMoisture())
                .idealLightIntensity(request.getIdealLightIntensity())
                .growthTarget(request.getGrowthTarget())
                .build();
        Plant plant = plantService.createPlant(dto, user, image);
        return ResponseEntity.ok(PlantDto.from(plant));
    }

    @DeleteMapping("/plant/delete/{plantId}")
    @Operation(summary = "식물 삭제하기", description = "식물을 이미지와 함께 삭제합니다")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "plantId") Long plantId){
        plantService.deletePlantById(plantId);
        return ResponseEntity.noContent().build(); // 삭제 후 응답없음.
    }
}