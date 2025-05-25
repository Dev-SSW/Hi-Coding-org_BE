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
        Optional<PlantGrowthLog> optionalLog = plantGrowthLogRepository
                .findFirstByPlantIdAndRecord(plantId, recordDate);
        int newGrowth = plantGrowthLogRequest.getGrowth();         //입력된 성장 길이

        // 3. 일기가 없다면 새로 생성
        if (optionalLog.isEmpty()) {
            PlantGrowthLog growthLog = PlantGrowthLog.createLog(plant, plantGrowthLogRequest);
            plantGrowthLogRepository.save(growthLog);
            if (newGrowth > plant.getTotalGrowth()) {      //현재 식물 전체 길이보다 큰 값을 입력 받았다면 식물 전체 길이를 업데이트
                plant.updateGrowth(newGrowth);
            }
            return growthLog.getId();
        }

        // 4. 과거에 기록이 있다면 덮어쓰기
        PlantGrowthLog existLog = optionalLog.get();
        int oldGrowth = existLog.getGrowth();               //기존 길이 가져오기
        existLog.updateLog(newGrowth, plantGrowthLogRequest.getContent(), recordDate);  //일단 입력된 길이와 내용으로 수정
        int currentMax = getMaxGrowth(plantId);             //현재 기록 중 가장 큰 길이를 가져옴

        if (newGrowth > plant.getTotalGrowth()) {           //현재 식물 전체 길이보다 큰 값을 입력 받았다면 식물 전체 길이를 업데이트
            plant.updateGrowth(newGrowth);
        }
        else if (plant.getTotalGrowth() == oldGrowth && newGrowth < oldGrowth) {
            //현재 식물 전체 길이와 이전 기록의 길이가 같거나, 새로운 길이 입력이 이전 기록의 길이보다 작다면
            plant.updateGrowth(currentMax); //전체를 다시 검색해서 가장 큰 값을 현재 식물 전체 길이로 저장시킨다.
        }
        return existLog.getId();
    }

    /* 내부 메서드: 현재 식물의 모든 기록 중 최대 성장 길이 반환 */
    private int getMaxGrowth(Long plantId) {
        return plantGrowthLogRepository.findByPlantId(plantId).stream()
                .mapToInt(PlantGrowthLog::getGrowth)
                .max()
                .orElse(0);
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
