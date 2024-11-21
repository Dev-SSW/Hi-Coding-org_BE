package com.example.codingmall.Payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "payment", description = "결제 관리 Api")
@RestController
@RequestMapping("public/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 정보 생성",description = "결제 정보를 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> createPayment(@RequestBody PaymentDto paymentDto){
        Long paymentId = paymentService.createPayment(paymentDto);
        return ResponseEntity.ok(paymentId);
    }

    @Operation(summary = "결제 처리",description = "결제 상태를 대기중 -> 결제 완료로 변경합니다.")
    @PostMapping("/{paymentId}/process")
    public ResponseEntity<Void> processPayment(@PathVariable Long paymentId){
        paymentService.processPayment(paymentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "결제 정보 가져오기" , description = "특정 결제 정보를 가져옵니다.")
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long paymentId){
        Payment payment = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(payment);
    }
    @Operation(summary = "결제 상태별 리스트 조회",description = "결제 상태별로 모든 리스트를 조회합니다.")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable PaymentStatus status){
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
    @Operation(summary = "결제 환불처리",description = "결제 상태를 환불로 변경합니다.")
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<Void> refundPayment(@PathVariable Long paymentId){
        paymentService.refundPayment(paymentId);
        return ResponseEntity.ok().build();
    }
}
