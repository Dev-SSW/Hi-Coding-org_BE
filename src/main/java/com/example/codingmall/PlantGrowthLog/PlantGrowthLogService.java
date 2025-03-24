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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantGrowthLogService {
    private final PlantGrowthLogRepository plantGrowthLogRepository;
    private final PlantRepository plantRepository;

    /* 성장 기록 저장 */
    @Transactional
    public Long recordGrowth(Long plantId, PlantGrowthLogRequest plantGrowthLogRequest) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new PlantNotFoundException("해당 식물을 찾을 수 없습니다."));
        int totalGrowth = plantGrowthLogRequest.getGrowth() + plant.getTotalGgrowth();   //전체 성장 길이 Plant에 저장
        plant.setTotalGrowth(totalGrowth);
        plantRepository.save(plant);

        int percentage = (int) ((double) totalGrowth / plant.getGrowthTarget() * 100); //성장률 계산
        PlantGrowthLog growthLog = PlantGrowthLog.createLog(plant, plantGrowthLogRequest, percentage, totalGrowth);
        plantGrowthLogRepository.save(growthLog);
        return growthLog.getId();
    }


    /* 특정 날짜의 성장 기록 단일 조회 */
    public PlantGrowthLog getGrowthLogByDate(Long plantId, LocalDate date) {
        return plantGrowthLogRepository.findFirstByPlantIdAndRecord(plantId, date).orElseThrow(() -> new PlantLogsNotFoundByDate("해당 날짜의 성장 기록이 없습니다."));
    }

    public List<PlantGrowthLog> getAllGrowthLogs(Long plantId) {
        return plantGrowthLogRepository.findByPlantId(plantId);
    }
}
