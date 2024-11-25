package com.example.codingmall.Delivery;

import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findByStatus(DeliveryStatus status);
    Optional<Delivery> findByOrder_Id(Long orderId);
    default Delivery findByDeliveryId(Long id){
        return findById(id).orElseThrow(()-> new IllegalStateException("이러한 배송 아이디를 찾을 수 없습니다. " + id));
    }
    default Delivery findByOrderId(Long orderId){
        return findByOrder_Id(orderId).orElseThrow(() -> new IllegalStateException("이러한 주문 내역 아이디가 없습니다. " + orderId));
    }
}
