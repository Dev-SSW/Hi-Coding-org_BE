package com.example.codingmall.Order;

import com.example.codingmall.Exception.OrderIdNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findOrderById(Long orderId) {
        return findById(orderId).orElseThrow(() -> new OrderIdNotFoundException("존재하지 않는 주문 ID 입니다 : " + orderId));
    }
}
