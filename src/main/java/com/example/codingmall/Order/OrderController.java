package com.example.codingmall.Order;

import com.example.codingmall.CouponPublish.CouponPublish;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.OrderItem.OrderItemRequest;
import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/user/order/create")
    @Operation(summary = "주문 생성")
    public ResponseEntity<Long> create(@AuthenticationPrincipal User user, @RequestBody OrderRequest orderRequest, @RequestParam(value = "couponId", required = false) Long couponPublishId){
        return ResponseEntity.ok(orderService.createOrder(user, orderRequest, couponPublishId));
    }

    @PostMapping("/user/order/create/fromCart")
    @Operation(summary = "장바구니로부터 주문 생성")
    public ResponseEntity<Long> createFromCart(@AuthenticationPrincipal User user, @RequestBody OrderCartRequest orderCartRequest, @RequestParam(value = "couponId", required = false) Long couponPublishId) {
        return ResponseEntity.ok(orderService.createOrderFromCart(user, orderCartRequest, couponPublishId));
    }

    @PostMapping("/user/order/cancel")
    @Operation(summary = "주문 취소")
    public ResponseEntity<Void> cancelOrder(@RequestParam("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/order")
    @Operation(summary = "주문/결제 페이지 데이터 조회")
    public ResponseEntity<OrderPageResponse> getOrderPageData(@AuthenticationPrincipal User user, @RequestBody List<OrderItemRequest> orderItems) {
        return ResponseEntity.ok(orderService.getOrderPageData(user, orderItems));
    }

    @GetMapping("user/order/history")
    @Operation(summary = "주문 내역 페이지 조회")
    public ResponseEntity<List<OrderHistoryResponse>> getOrderHistoryData(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getOrderHistoryData(user));
    }
}
