package com.example.codingmall.Payment;

import com.example.codingmall.Order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "payment", description = "결제 관리 Api")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 정보 생성",description = "결제 정보를 생성합니다.")
    @PostMapping("/admin/payment/create/{orderId}")
    public ResponseEntity<Payment> createPayment(@PathVariable(name = "orderId") Long orderId){
        Payment payment = paymentService.createPayment(orderId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "결제 처리",description = "결제 상태를 대기중 -> 결제 완료로 변경합니다.")
    @PostMapping("/admin/payment/process/{paymentId}")
    public ResponseEntity<Payment> processPayment(@PathVariable(name = "paymentId") Long paymentId){
        Payment payment = paymentService.processPayment(paymentId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "결제 정보 가져오기" , description = "특정 결제 정보를 가져옵니다.")
    @GetMapping("/admin/payment/getInfo/{paymentId}")
    public ResponseEntity<Payment> getPayment(@PathVariable(name = "paymentId") Long paymentId){
        Payment payment = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(payment);
    }
    @Operation(summary = "결제 상태별 리스트 조회",description = "결제 상태별로 모든 리스트를 조회합니다.")
    @GetMapping("/admin/payment/getStatus/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable(name = "status") PaymentStatus status){
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
    @Operation(summary = "결제 환불처리",description = "결제 상태를 환불로 변경합니다.")
    @PostMapping("/admin/payment/refund/{paymentId}")
    public ResponseEntity<Void> refundPayment(@PathVariable(name = "paymentId") Long paymentId){
        paymentService.refundPayment(paymentId);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "결제 리스트 조회" , description = "사용자가 결제한 항목을 리스트 형식으로 조회합니다.")
    @GetMapping("/admin/payment/searchList")
    public List<PaymentListDto> getPayments(){
        return paymentService.getPaymentList();
    }
}
