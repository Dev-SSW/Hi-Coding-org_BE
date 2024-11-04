package com.example.codingmall.Cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartDto> addItemToCart(@RequestParam Long userId, @RequestParam Long itemId, @RequestParam int count){
        CartDto cartDto = cartService.addItemToCart(userId, itemId, count);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId){
        CartDto cartDto = cartService.getCartToUser(userId);
        return ResponseEntity.ok(cartDto);
    }

}
