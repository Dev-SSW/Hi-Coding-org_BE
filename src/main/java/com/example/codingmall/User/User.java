package com.example.codingmall.User;

import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.mapping.Join;
import java.util.List;

@Entity
@Getter
@Table(name = "User")

public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    private String username;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

}
