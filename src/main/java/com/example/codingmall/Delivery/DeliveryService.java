package com.example.codingmall.Delivery;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Long saveDelivery(DeliveryDto deliveryDto){
        Delivery delivery = deliveryDto.toEntity();
        return deliveryRepository.save(delivery).getId();
    }
    // 배송 정보 조회
    public Delivery findDelivery(Long id){
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("배송 정보가 없습니다."));
    }
    @Transactional
    public void updateStatus(Long id, boolean status){
        Delivery delivery = findDelivery(id);
        delivery.updateStatus(status);
    }
    @Transactional
    public void deleteDelivery(Long id){
        deliveryRepository.deleteById(id);
    }
    public Delivery findDeliveryByOrder(Long id){
        return deliveryRepository.findByOrderId(id);
    }
    // 결제 상태 업데이트
    @Transactional
    public void updatePaymentstatus(Long deliveryId,boolean status){
        Delivery delivery = findDelivery(deliveryId);
        delivery.updateStatus(status);
    }
    // 모든 배송 목록 조회
    public List<Delivery> findAllDeliveries(){
        return deliveryRepository.findAll();
    }

    // 카드사별 배송 목록 조회
    public List<Delivery> findDeliveriesByCardCompany(String cardCompany){
        return deliveryRepository.findByCardCompany(cardCompany);
    }
}

