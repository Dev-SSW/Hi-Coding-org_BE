package com.example.codingmall.Order;

import com.example.codingmall.Exception.OrderIdNotFoundException;
import com.example.codingmall.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findOrderById(Long orderId) {
        return findById(orderId).orElseThrow(() -> new OrderIdNotFoundException("존재하지 않는 주문 ID 입니다 : " + orderId));
    }
    List<Order> findByUser(User user);

    boolean existsByUser(User user);
}
