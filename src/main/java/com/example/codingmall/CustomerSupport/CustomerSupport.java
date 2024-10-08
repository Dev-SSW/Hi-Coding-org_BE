package com.example.codingmall.CustomerSupport;

import com.example.codingmall.Category.Category;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "CustomerSupport")
public class CustomerSupport {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고객지원 요청 고유 식별자 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User customorUser; // 요청 사용자 id

    @OneToOne
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
