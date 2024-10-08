package com.example.codingmall.Review;

import com.example.codingmall.Item.Item;
import com.example.codingmall.Order.Order;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Review")
public class Review {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String comment;
    private float rating; // 별점.(.5단위)

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;


}
