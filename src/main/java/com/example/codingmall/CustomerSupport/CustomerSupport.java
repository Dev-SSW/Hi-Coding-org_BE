package com.example.codingmall.CustomerSupport;

import com.example.codingmall.Category.Category;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class CustomerSupport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customersupport_id")
    private Long id; // 고객지원 요청 고유 식별자 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 요청 사용자, 관리자 사용자를 구분하는 것은 여기서 구분하는 것이 아니다.

    @OneToOne(fetch = FetchType.LAZY)
    private Category category;

    private String title;
    private String content;
    private Status status;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    private enum Status{
        done,undone // 답변완료, 답변 비완료
    }

}
