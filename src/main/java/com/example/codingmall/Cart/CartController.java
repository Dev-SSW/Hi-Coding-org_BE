package com.example.codingmall.Cart;

import com.example.codingmall.User.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartDto> addItemToCart(@AuthenticationPrincipal User user,
                                                 @RequestBody AddCartItemRequest request,
                                                 @RequestParam(name = "count") int count){
        CartDto cartDto = cartService.addItemToCart(user,request);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal User user){
        CartDto cartDto = cartService.getCartToUser(user);
        return ResponseEntity.ok(cartDto);
    }
}
