package com.example.codingmall.Cart;

import com.example.codingmall.Exception.CartNotFoundException;
import com.example.codingmall.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser (User user);
    default Cart findCartByUser(User user) {
        return findByUser(user).orElseThrow(() ->new CartNotFoundException(+ user.getId() + " 유저의 장바구니를 찾지 못했습니다." ));
    }
}
