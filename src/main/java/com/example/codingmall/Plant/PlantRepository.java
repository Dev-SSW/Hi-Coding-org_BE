package com.example.codingmall.Plant;

import com.example.codingmall.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository

public interface PlantRepository extends JpaRepository<Plant,Long> {
    List<Plant> findAllByUser(User user);
    Optional<Plant> findPlantByUser (User user);

    default Plant findPlantByPlantId(Long plantId){
        return findById(plantId).orElseThrow(() -> new IllegalStateException("plantId를 찾을 수 없습니다." + plantId ));
    }
    default Plant defaultfindPlantByUser(User user){
        return findPlantByUser(user).orElseThrow(() ->new IllegalStateException("findPlantByUser 오류입니다."));
    }
}
