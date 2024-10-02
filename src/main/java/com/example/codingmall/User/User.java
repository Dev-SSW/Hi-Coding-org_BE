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
    @Column(name = "user_id")
    private Long userId; // 회원고유번호

    private String username; // 아이디

    @Column(nullable = false, length = 13)
    private int jumin; // 주민등록번호
    @Column(nullable = false)
    private int name; //이름

    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Status status; // 상태(탈퇴회원 여부 파악)

    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;


    private enum Status {
        ACTIVATE,
        DEACTIVATE // 탈퇴회원
    }

}
