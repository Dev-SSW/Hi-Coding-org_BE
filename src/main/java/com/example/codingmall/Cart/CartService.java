package com.example.codingmall.Cart;

import com.example.codingmall.CartItem.CartItem;
import com.example.codingmall.CartItem.CartItemDto;
import com.example.codingmall.CartItem.CartItemReposiroty;
import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemReposiroty cartItemReposiroty;
    private final UserRepository userRepository;


    @Transactional
    public CartDto addItemToCart(Long userId, Long itemId,int count){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Invalid user Id"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("Invalid Item id"));
        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));
        CartItem cartItem = new CartItem(item, count);
        cart.addItem(cartItem);

        cartRepository.save(cart);
        return new CartDto(cart);
    }
    @Transactional
    public CartDto getCartToUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Invalid user Id"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user"));

        return new CartDto(cart);
    }
}

