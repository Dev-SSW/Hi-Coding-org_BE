package com.example.codingmall.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //ID로 User 찾기
    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    default User findUserByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username에 일치하는 User가 존재하지 않습니다 : " + username));
    }
}
