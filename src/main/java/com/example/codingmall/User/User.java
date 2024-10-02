package com.example.codingmall.User;

import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.mapping.Join;
<<<<<<< HEAD

import java.util.ArrayList;
=======
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
>>>>>>> main
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Table(name = "User")
public class User implements UserDetails, OAuth2User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; // 회원고유번호

    private String username; // 아이디
    private String password; // 비밀번호
    @Column(nullable = false, length = 13)
    private int jumin;       // 주민등록번호
    @Column(nullable = false)
    private String name;     // 사용자 이름
    private String email;    // 이메일
    private String phoneNumber;// 휴대폰 번호

    @Enumerated(EnumType.STRING)
    private Status status;   // 상태(탈퇴 회원 여부 파악)

    private Role role;       // 역할

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Transient
    private Map<String, Object> attributes; //OAuth2 속성

    private enum Status {
        ACTIVATE,
        DEACTIVATE // 탈퇴회원
    }

    //UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() { return null; }
    // OAuth2User
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getUsername() {
        return username;
    }
}
