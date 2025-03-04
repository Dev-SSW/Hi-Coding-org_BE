package com.example.codingmall.OrderItem;

import com.example.codingmall.Item.Item;
import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  // 주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문 번호

    private int itemCount;          // 주문 상품 갯수
    private int orderPrice;         // 주문 상품 가격

    /* 생성 메서드 (정적 팩토리)*/
    public static OrderItem createOrderItem(Item item, int itemCount) {
        int Price = item.getPrice();  // 상품 가격
        return OrderItem.builder()
                .item(item)
                .itemCount(itemCount)
                .orderPrice(Price)
                .build();
    }

    /* 연관 관계 편의 메서드 */
    public void setOrder(Order order) {
        this.order = order;
    }

    /* 비즈니스 로직 */
    public void cancel() { getItem().addStock(itemCount); }

    /* 조회 로직 */
    public int getTotalPrice() { return getOrderPrice() * getItemCount(); }
}
