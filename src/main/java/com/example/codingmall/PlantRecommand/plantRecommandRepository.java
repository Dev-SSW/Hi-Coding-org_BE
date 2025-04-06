package com.example.codingmall.PlantRecommand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface plantRecommandRepository extends JpaRepository<PlantRecommand,Long> {
}
