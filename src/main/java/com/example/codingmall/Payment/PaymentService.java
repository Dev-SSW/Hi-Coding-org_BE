package com.example.codingmall.Payment;

import com.example.codingmall.Exception.PaymentAlreadyHasException;
import com.example.codingmall.Order.Order;
import com.example.codingmall.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    // 결제 정보 생성
    @Transactional
    public Payment createPayment(Long orderId){
        Order order = orderRepository.findOrderById(orderId);

        if (order.getPayment() != null){
            throw new PaymentAlreadyHasException("orderId" + orderId + "hasAlreadyHasPaymentException");
        }
        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .cardCompany("DEFAULTE")
                .paymentMethod(PaymentMethod.card)
                .build();
        paymentRepository.save(payment);
        return payment;
    }
    // 결제 정보 처리
    @Transactional
    public Payment processPayment(Long paymentId){
        Payment payment = paymentRepository.findPaymentById(paymentId);
        payment.updateStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        return payment;
    }
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findByIdWithOrder(paymentId);
    }
    public List<Payment> getPaymentsByStatus(PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }
    // 환불처리
    @Transactional
    public void refundPayment (Long paymentId){
        Payment payment = paymentRepository.findPaymentById(paymentId);
        payment.updateStatus(PaymentStatus.REFUNDED);
    }
    public List<PaymentListDto> getPaymentList(){
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(PaymentListDto::new)
                .collect(Collectors.toList());
    }
}

