package com.example.codingmall.Cart;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
@Tag(name = "Cart" , description = "특정 물품을 장바구니에 추가하고, 장바구니를 가져오는 api")
@Transactional(readOnly = true)
public class CartController {
    private final CartService cartService;

    @PutMapping("/add/{userId}")
    @Operation(summary = "제품 장바구니에 추가")
    @Transactional
    public ResponseEntity<CartDto> addItemToCart(@AuthenticationPrincipal User user,
                                                 @RequestBody AddCartItemRequest request){
        CartDto cartDto = cartService.addItemToCart(user,request);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "장바구니 가져오기")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal User user){
        CartDto cartDto = cartService.getCartToUser(user);
        return ResponseEntity.ok(cartDto);
    }
}
