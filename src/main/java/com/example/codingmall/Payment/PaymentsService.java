package com.example.codingmall.Payment;

import com.example.codingmall.Order.Order;
import com.example.codingmall.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentsService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    // 결제 정보 생성
    @Transactional
    public Long createPayment(PaymentDto paymentDto){
        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new IllegalStateException("그러한 주문 번호가 없습니다. 주문번호 : " + paymentDto.getOrderId()));
        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentDto.getAmount())
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .cardCompany(paymentDto.getCardCompany())
                .paymentMethod(paymentDto.getPaymentMethod())
                .build();

        payment.setOrder(order);
        return paymentRepository.save(payment).getId();
    }
    // 결제 정보 처리
    @Transactional
    public void processPayment(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalStateException("그러한 결제 ID가 없습니다." + paymentId));
        payment.updateStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
    }
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findByIdWithiOrder(paymentId);
    }
    public List<Payment> getPaymentsByStatus(PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }
    // 환불처리
    @Transactional
    public void refundPayment (Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalStateException("환불할 수 있는 결제 정보가 없습니다. " + paymentId));
        payment.updateStatus(PaymentStatus.REFUNDED);
    }
}

