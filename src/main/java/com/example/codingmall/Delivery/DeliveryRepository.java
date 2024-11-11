package com.example.codingmall.Delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    Delivery findByOrderId(Long orderId);
    // 카드사별 배송 목록 찾기
    List<Delivery> findByCardCompany(String cardCompany);
    // 결제 상태별 배송 목록 찾기
    List<Delivery> findByStatus(boolean status);
}
