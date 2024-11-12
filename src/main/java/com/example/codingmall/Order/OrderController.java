package com.example.codingmall.Order;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "주문 생성")
    public ResponseEntity<Long> create(@AuthenticationPrincipal User user, @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.createOrder(user, orderRequest));
    }

    @PostMapping("/create/fromCart")
    @Operation(summary = "장바구니로부터 주문 생성")
    public ResponseEntity<Long> createFromCart(@AuthenticationPrincipal User user, @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrderFromCart(user, orderRequest));
    }

    @PostMapping("/cancel")
    @Operation(summary = "주문 취소")
    public ResponseEntity<Void> cancelOrder(@RequestParam("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
