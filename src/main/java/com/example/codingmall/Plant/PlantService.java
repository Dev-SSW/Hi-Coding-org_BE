package com.example.codingmall.Plant;
import com.example.codingmall.Exception.UserNotFoundException;
import com.example.codingmall.S3.S3Service;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public List<PlantDto> findAllPlants(User user) {
        List<Plant> plants = plantRepository.findAllByUser(user);
        return plants.stream()
                .map(PlantDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Plant createPlant(PlantDto plantDto , User user, MultipartFile file) throws IOException {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("Plant에서 User을 찾을 수 없음."));
        String imageUrl = s3Service.uploadFile(file);
        Plant plant = plantDto.toEntity(user);
        plant.setImageUrl(imageUrl);
        return plantRepository.save(plant);
    }

    @Transactional
    public Plant updatePlant(PlantDto plantDto, User user) {
        Plant plant = plantRepository.findPlantByPlantId(plantDto.getId());
        if (!plant.getUser().getId().equals(user.getId())){
            throw new UserNotFoundException("User Id and from plant userId are not equal");
        }

        plant.updatePlant(plantDto);

        return plant;
    }
}

