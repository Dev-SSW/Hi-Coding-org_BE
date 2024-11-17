package com.example.codingmall.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    // 카드사별 배송 목록 찾기
    Payment findByOrderId(Long orderId);
    // 결제 상태별 배송 목록 찾기
    List<Payment> findByCardCompany(String cardCompany);
// 결제 상태별 배송 목록 찾기
    List<Payment> findByStatus(PaymentStatus status);

    @Query("SELECT p FROM Payment p JOIN FETCH p.order where p.id = :id")
    Payment findByIdWithiOrder(@Param("id") Long id);

}
