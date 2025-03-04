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

    private LocalDateTime registerDate; //등록일시
    private LocalDateTime updateDate;   //수정일시

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
