package com.example.codingmall.PlantRecommand;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class PlantRecommandService {
    private final plantRecommandRepository plantRecommandRepository;
    private static final Map<String,List<String>> mbtiToPlantMap = new HashMap<>();
    private final Random random = new Random();

    static {
        mbtiToPlantMap.put("INTJ", List.of(
                "몬스테라 - 독립적이고 고요한 공간을 좋아하는 INTJ에게 잘 어울리는 식물입니다.",
                "페페로미아 - 자기 관리를 잘하는 성향과 잘 맞습니다."
        ));
        mbtiToPlantMap.put("INTP", List.of(
                "필로덴드론 - 탐구심 많고 사색을 즐기는 INTP에게 지적 영감을 주는 식물입니다.",
                "트리안 - 조용한 분위기 속에서 잘 자라며, 사고 중심의 INTP와 잘 어울립니다."
        ));
        mbtiToPlantMap.put("ENTJ", List.of(
                "고무나무 - 결단력 있고 카리스마 있는 ENTJ에게 강인한 이미지를 주는 식물입니다.",
                "아레카야자 - 활동적이고 리더십 있는 ENTJ와 함께 생명력을 뿜어냅니다."
        ));
        mbtiToPlantMap.put("ENTP", List.of(
                "틸란드시아 - 독창적이고 창의적인 ENTP에게 새로운 자극을 주는 식물입니다.",
                "칼라디움 - 변화무쌍한 매력을 지닌 ENTP에게 어울리는 다채로운 잎을 가진 식물입니다."
        ));
        mbtiToPlantMap.put("INFJ", List.of(
                "플루메리아 - 깊은 통찰력을 지닌 INFJ에게 정서적 안정감을 주는 식물입니다.",
                "스노우사랑초 - 조용한 안식처를 원하는 INFJ에게 잘 어울립니다."
        ));
        mbtiToPlantMap.put("INFP", List.of(
                "라벤더 - 감성적이고 이상주의적인 INFP에게 평온함을 주는 향기로운 식물입니다.",
                "무늬아이비 - 소박하지만 독특한 아름다움을 지닌 식물로 INFP와 닮았습니다."
        ));
        mbtiToPlantMap.put("ENFJ", List.of(
                "호야 - 타인을 돌보는 따뜻한 ENFJ에게 사랑을 상징하는 식물입니다.",
                "파키라 - 사람들과의 조화를 중요시하는 ENFJ에게 행운을 불러오는 식물입니다."
        ));
        mbtiToPlantMap.put("ENFP", List.of(
                "선인장 - 활기차고 자유로운 ENFP에게 어울리는 자유로운 느낌의 식물입니다.",
                "스투키 - 에너지가 넘치고 개성이 강한 ENFP와 찰떡입니다."
        ));
        mbtiToPlantMap.put("ISTJ", List.of(
                "산세베리아 - 질서를 중요시하는 ISTJ에게 깔끔하고 규칙적인 성장의 상징입니다.",
                "벤자민고무나무 - 꾸준하고 안정적인 성향과 잘 어울립니다."
        ));
        mbtiToPlantMap.put("ISFJ", List.of(
                "스파티필름 - 조용하고 배려심 많은 ISFJ에게 안정감을 주는 식물입니다.",
                "산세베리아 - 인내심 강하고 안정적인 성향과 잘 맞습니다."
        ));
        mbtiToPlantMap.put("ESTJ", List.of(
                "용설란 - 강한 책임감과 실용적인 ESTJ에게 잘 어울리는 단단한 식물입니다.",
                "유칼립투스 - 깔끔하고 정돈된 분위기를 좋아하는 ESTJ에게 추천합니다."
        ));
        mbtiToPlantMap.put("ESFJ", List.of(
                "수박페페 - 주변 사람을 챙기는 ESFJ에게 생동감을 주는 밝은 식물입니다.",
                "골든포토스 - 타인과 잘 어울리는 친화력 있는 ESFJ에게 추천하는 식물입니다."
        ));
        mbtiToPlantMap.put("ISTP", List.of(
                "디펜바키아 - 조용하지만 실용적인 ISTP에게 잘 맞는 실내 정화 식물입니다.",
                "테이블야자 - 혼자만의 공간을 즐기는 ISTP에게 어울리는 자연스러운 식물입니다."
        ));
        mbtiToPlantMap.put("ISFP", List.of(
                "스킨답서스 - 감성적이고 자유로운 ISFP에게 부드러운 분위기를 주는 식물입니다.",
                "칼라디아 - 예술적인 감각을 가진 ISFP에게 아름다운 무늬의 식물이 어울립니다."
        ));
        mbtiToPlantMap.put("ESTP", List.of(
                "알로카시아 - 도전적인 ESTP에게 강한 존재감을 주는 이국적인 식물입니다.",
                "에어플랜트 - 자유롭고 활동적인 ESTP와 궁합이 좋은 공중식물입니다."
        ));
        mbtiToPlantMap.put("ESFP", List.of(
                "무화과나무 - 사람들과의 즐거움을 추구하는 ESFP에게 풍요로움을 상징하는 식물입니다.",
                "제라늄 - 화사한 에너지를 뿜는 ESFP에게 어울리는 컬러풀한 식물입니다."
        ));
    }
    public String getMBTIResult(MBTIResponseDto mbtiResponseDto) {
        String mbti = calculateMBTI(mbtiResponseDto);
        List<String> plant = mbtiToPlantMap.getOrDefault(mbti,
                List.of("스칸답서스 - 어떤 성격 유형과도 잘 어울리는 식물입니다."));

        String selectedPlant = plant.get(random.nextInt(plant.size()));
        PlantRecommand result = new PlantRecommand(
                null,
                mbti,
                selectedPlant,
                Timestamp.from(Instant.now())
        );
        plantRecommandRepository.save(result);
        return selectedPlant;
    }
    private String calculateMBTI(MBTIResponseDto dto){
        StringBuilder sb = new StringBuilder();
        sb.append(dto.getE() >= dto.getI() ? "E" : "I");
        sb.append(dto.getN() >= dto.getS() ? "N" : "S");
        sb.append(dto.getT() >= dto.getF() ? "T" : "F");
        sb.append(dto.getP() >= dto.getJ() ? "P" : "J");
        return sb.toString();
    }
}
