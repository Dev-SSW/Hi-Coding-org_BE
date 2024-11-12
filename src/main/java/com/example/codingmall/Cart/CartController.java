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
    public ResponseEntity<CartDto> addItemToCart(@RequestParam(name = "userId") Long userId,
                                                 @RequestParam(name = "itemId") Long itemId,
                                                 @RequestParam(name = "count") int count){
        CartDto cartDto = cartService.addItemToCart(userId, itemId, count);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name = "userId") Long userId){
        CartDto cartDto = cartService.getCartToUser(userId);
        return ResponseEntity.ok(cartDto);
    }

}
