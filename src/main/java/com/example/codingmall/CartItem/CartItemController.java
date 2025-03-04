package com.example.codingmall.CartItem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlin.PublishedApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "CarItem" , description = "장바구니의 특정 물품 갯수를 변경/삭제하는 api")
public class CartItemController {
    private final CartItemService cartItemService;

    @PutMapping("user/cartItem/update/{cartItemId}")
    @Operation(summary = "장바구니에 담겨있는 특정 물품의 갯수 변경", description = "장바구니에 담겨 있는 하나의 물품에 대해 갯수를 변경합니다.")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable(name = "cartItemId") Long cartItemId, @RequestParam(name = "count") int count) {
        CartItemDto cartItemDto = cartItemService.updateCartItemCount(cartItemId, count);
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("user/cartItem/remove/{cartItemId}")
    @Operation(summary = "장바구니에 담겨있는 특정 물품 삭제" , description =  "장바구니에 담겨 있는 하나의 물품에 대해 삭제합니다.")
    public ResponseEntity<Void> removeCartItem(@PathVariable(name = "cartItemId") Long cartItemId) {
        cartItemService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
