package com.example.codingmall.User;

import com.example.codingmall.Cart.Cart;
import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User implements UserDetails, OAuth2User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; // 회원고유번호

    private String username; // 아이디
    private String password; // 비밀번호
    @Column(nullable = false,name = "birth",columnDefinition = "DATE")
    private LocalDate birth;       //  생년월일
    @Column(nullable = false)
    private String name;     // 사용자 이름
    private String email;    // 이메일
    private String phoneNumber;// 휴대폰 번호
    @Enumerated(EnumType.STRING)
    private UserStatus status;   // 상태(탈퇴 회원 여부 파악)
    private Role role;       // 역할

    @Builder.Default // 리스트 초깃값 설정을 위한 어노테이션(warnings 해결)
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    @Transient
    private Map<String, Object> attributes; //OAuth2 속성

    public void setRole(Role role) {
        this.role = role;
    }

    @Builder // 비밀번호 변경
    public User (Long id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User changePassword(String encodedPassword){
        this.password = encodedPassword;
        return this;
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
