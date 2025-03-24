package com.example.codingmall.PlantGrowthLog;

import com.example.codingmall.Plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlantGrowthLogRepository extends JpaRepository<PlantGrowthLog, Long> {
    Optional<PlantGrowthLog> findFirstByPlantIdAndRecord(Long plantId, LocalDate record);
    List<PlantGrowthLog> findByPlantId(Long plantId);
}
