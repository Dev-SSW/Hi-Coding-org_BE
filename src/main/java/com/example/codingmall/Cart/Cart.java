package com.example.codingmall.Cart;

import com.example.codingmall.CartItem.CartItem;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();


    private int count; // 상품 수량
    private int price; // 상품 가격
    // --> price : CartItem의 합산 가격을 넣을 필요가 있어보입니다.
    // --> count : 전체 물품의 개수를 넣으면 될 것 같습니다. (이건 없어도 될 수도?)
    // -----> 이건 cartDto에 calculatetotalPrice 메서드로 구현했음.

    private LocalDateTime registerDate;// 등록일시
    private LocalDateTime updateDate; //수정일시

    public  Cart (User user){
        this.user = user;
        this.registerDate = LocalDateTime.now();
    }
    public void addItem(CartItem cartItem){
        items.add(cartItem);
        cartItem.setCart(this);
        this.updateDate = LocalDateTime.now();
    }

}
