package com.example.codingmall.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //ID로 User 찾기
    Optional<User> findByUsername(String username);

    //Optional 없이 가져오기
    User findUserByUsername(String username);
}
