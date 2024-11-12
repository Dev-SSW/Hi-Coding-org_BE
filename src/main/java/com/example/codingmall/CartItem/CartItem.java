package com.example.codingmall.CartItem;

import com.example.codingmall.Cart.Cart;
import com.example.codingmall.Item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartitem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;

    private int count;
    private int price;

    public CartItem (Item item, int count){
        this.item = item;
        this.count = count;
        this.price = item.getPrice() * count; // 총 금액

    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void updateCount(int newCount) {
        this.count = newCount;
        this.price = this.item.getPrice() * newCount;
    }
}
