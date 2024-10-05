package com.example.codingmall.Teacher;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Teacher")
public class Teacher {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String intro;
    private boolean status; // 상태 true -> 표시, false ->비표시
    private String classUrl; // 강의 url
}
