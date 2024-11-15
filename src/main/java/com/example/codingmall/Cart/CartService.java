package com.example.codingmall.Cart;

import com.example.codingmall.CartItem.CartItem;
import com.example.codingmall.CartItem.CartItemDto;
import com.example.codingmall.CartItem.CartItemReposiroty;
import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public CartDto addItemToCart(User user, AddCartItemRequest request){

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("찾을 수 없는 아이템 아이디입니다." + request.getItemId()));
        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));

        CartItem cartItem = new CartItem(item, request.getCount());
        cart.addItem(cartItem);

        cartRepository.save(cart);
        return new CartDto(cart);
    }
    public CartDto getCartToUser(User user){
        userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalStateException("이러한 아이디를 찾지 못했습니다.: " + user.getUsername()));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("이러한 유저에 담겨있는 카트를 찾지 못했습니다."));

        return new CartDto(cart);
    }
}

