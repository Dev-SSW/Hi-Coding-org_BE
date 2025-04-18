package com.example.codingmall.Cart;

import com.example.codingmall.CartItem.CartItem;
import com.example.codingmall.CartItem.CartItemRepository;
import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


    @Transactional
    public CartDto addItemToCart(User user, AddCartItemRequest request){

        Item item = itemRepository.findItemByItemId(request.getItemId());
        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));

        CartItem cartItem = new CartItem(item, request.getCount());
        cart.addItem(cartItem);

        cartRepository.save(cart);
        return new CartDto(cart);
    }
    public CartDto getCartToUser(User user){
        userRepository.findUserByUsername(user.getUsername());
        Cart cart = cartRepository.findCartByUser(user);

        return new CartDto(cart);
    }
}

