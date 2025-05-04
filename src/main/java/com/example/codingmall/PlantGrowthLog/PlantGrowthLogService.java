package com.example.codingmall.PlantGrowthLog;

import com.example.codingmall.Exception.PlantLogsNotFoundByDate;
import com.example.codingmall.Exception.PlantNotFoundException;
import com.example.codingmall.Plant.Plant;
import com.example.codingmall.Plant.PlantDto;
import com.example.codingmall.Plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantGrowthLogService {
    private final PlantGrowthLogRepository plantGrowthLogRepository;
    private final PlantRepository plantRepository;

    /* 성장 기록 저장 */
    @Transactional
    public Long recordGrowth(Long plantId, PlantGrowthLogRequest plantGrowthLogRequest) {
        // 1. 해당 식물 찾기
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantNotFoundException("해당 식물을 찾을 수 없습니다."));
        // 2. 해당 날짜에 일기가 있는지 확인
        LocalDate recordDate = plantGrowthLogRequest.getRecord();
        PlantGrowthLog existLog = plantGrowthLogRepository
                .findFirstByPlantIdAndRecord(plantId, recordDate)
                .orElse(null);
        // 3. 일기가 없다면 새로 생성
        if (existLog == null) {
            int totalGrowth = plantGrowthLogRequest.getGrowth() + plant.getTotalGrowth();   //입력된 성장 길이 + 기존 식물의 성장 길이
            plant.updateGrowth(totalGrowth);                                                //식물의 성장 길이를 수정 (변경 감지로 수정)
            PlantGrowthLog growthLog = PlantGrowthLog.createLog(plant, plantGrowthLogRequest);
            plantGrowthLogRepository.save(growthLog);
            return growthLog.getId();
        }
        // 4. 기록이 있다면 덮어쓰기
        int originTotalGrowth = plant.getTotalGrowth();     //기존 저장 기록 시의 식물 전체 길이
        int originGrowth = existLog.getGrowth();            //기존 저장 기록의 성장 길이

        int finalGrowth;
        if (plantGrowthLogRequest.getGrowth() == 0) {       // 성장 길이 입력 값이 0이라면
            finalGrowth = originGrowth;                     // 기존 저장 기록의 성장 길이를 저장
        } else {                                            // 성장 길이 입력 값이 0이 아니라면
            finalGrowth = plantGrowthLogRequest.getGrowth();// 성장 길이 입력 값을 저장
        }
        int finalTotalGrowth = originTotalGrowth - originGrowth + finalGrowth;               // 전체 성장 길이 다시 구하기
        plant.updateGrowth(finalTotalGrowth);

        String finalContent = plantGrowthLogRequest.getContent();
        existLog.updateLog(finalGrowth, finalContent, existLog.getRecord());
        return existLog.getId();
    }


    /* 특정 날짜의 성장 기록 단일 조회 */
    public PlantGrowthLogResponse getGrowthLogByDate(Long plantId, LocalDate date) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantNotFoundException("해당 식물을 찾을 수 없습니다."));

        Optional<PlantGrowthLog> log = plantGrowthLogRepository.findFirstByPlantIdAndRecord(plantId, date);
        if (log.isPresent()) {
            //기록이 존재할 시
            PlantGrowthLog growthLog = log.get();
            return PlantGrowthLogResponse.builder()
                    .plantId(plantId)
                    .date(growthLog.getRecord())
                    .growth(growthLog.getGrowth())
                    .content(growthLog.getContent())
                    .totalGrowth(plant.getTotalGrowth())
                    .percentage(plant.getPercentage())
                    .build();
        } else {
            // 기록이 없는 경우, growth/log 내용은 null
            return PlantGrowthLogResponse.builder()
                    .plantId(plantId)
                    .date(date)
                    .growth(null)
                    .content(null)
                    .totalGrowth(plant.getTotalGrowth())
                    .percentage(plant.getPercentage())
                    .build();
        }
    }

    public List<PlantGrowthLog> getAllGrowthLogs(Long plantId) {
        return plantGrowthLogRepository.findByPlantId(plantId);
    }
}
