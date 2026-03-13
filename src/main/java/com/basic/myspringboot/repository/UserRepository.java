package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 엔티티를 DB에 저장/조회/수정/삭제하는 통로
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContains(String name);
    Optional<User> findByEmail(String email);
}
