package com.example.codingmall.PlantGrowthLog;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlantGrowthLogController {
    private final PlantGrowthLogService plantGrowthLogService;

    @PostMapping("/plant/PlantGrowthLog/create/{plantId}")
    @Operation(summary = "식물 성장 일기 날짜별로 등록하기")
    public ResponseEntity<String> recordGrowth(@PathVariable(name = "plantId") Long plantId, @RequestBody PlantGrowthLogRequest plantGrowthLogRequest) {
        plantGrowthLogService.recordGrowth(plantId, plantGrowthLogRequest);
        return ResponseEntity.ok("기록 저장이 완료되었습니다.");
    }

    @GetMapping("/plant/PlantGrowthLog/get/{plantId}")
    @Operation(summary = "식물 성장 지표 날짜별로 조회")
    public ResponseEntity<PlantGrowthLog> getGrowthLogsByDate(@PathVariable(name = "plantId") Long plantId,
                                                              @RequestParam(name = "date") String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return ResponseEntity.ok(plantGrowthLogService.getGrowthLogByDate(plantId, localDate));
    }

    @GetMapping("/plant/PlantGrowthLog/GetAll/{plantId}")
    @Operation(summary = "식물 성장 지표 전체 조회")
    public ResponseEntity<List<PlantGrowthLog>> getAllGrowthLogs(@PathVariable(name = "plantId") Long plantId) {
        return ResponseEntity.ok(plantGrowthLogService.getAllGrowthLogs(plantId));
    }

}
