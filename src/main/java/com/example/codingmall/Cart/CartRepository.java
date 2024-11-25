package com.example.codingmall.Cart;

import com.example.codingmall.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser (User user);
    default Cart findCartByUser(User user) {
        return findByUser(user).orElseThrow(() -> new IllegalStateException("유저의 장바구니를 찾지 못했습니다. userId : " + user.getId()));
    }
}
