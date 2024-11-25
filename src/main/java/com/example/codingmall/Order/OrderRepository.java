package com.example.codingmall.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findOrderById(Long orderId) {
        return findById(orderId).orElseThrow(() -> new IllegalStateException("존재하지 않는 주문 ID 입니다 : " + orderId));
    }
}
