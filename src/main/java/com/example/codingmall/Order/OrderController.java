package com.example.codingmall.Order;

import com.example.codingmall.User.Login.LoginDto.JwtResponse;
import com.example.codingmall.User.Login.LoginDto.SignupRequest;
import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "주문 생성")
    public ResponseEntity<CreateOrderResponse> create(@AuthenticationPrincipal User user, @RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.createOrder(user, orderDto));
    }
}
