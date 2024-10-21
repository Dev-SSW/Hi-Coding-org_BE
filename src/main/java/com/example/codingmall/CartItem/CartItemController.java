package com.example.codingmall.CartItem;

import kotlin.PublishedApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/cartitem")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long cartItemId, @RequestParam int count){
        CartItemDto cartItemDto = cartItemService.updateCartItemCount(cartItemId, count);
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId){
        cartItemService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
