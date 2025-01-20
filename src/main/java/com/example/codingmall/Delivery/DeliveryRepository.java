package com.example.codingmall.Delivery;

import com.example.codingmall.Exception.IdNotFoundException;
import com.example.codingmall.Exception.OrderIdNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findByStatus(DeliveryStatus status);
    Optional<Delivery> findByOrder_Id(Long orderId);
    default Delivery findByDeliveryId(Long id){
        return findById(id).orElseThrow(()-> new IdNotFoundException("이러한 배송 아이디를 찾을 수 없습니다. " + id));
    }
    default Delivery findByOrderId(Long orderId){
        return findByOrder_Id(orderId).orElseThrow(() -> new OrderIdNotFoundException("이러한 주문 내역 아이디가 없습니다. " + orderId));
    }
}
