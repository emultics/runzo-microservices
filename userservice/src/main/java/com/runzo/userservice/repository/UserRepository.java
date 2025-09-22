package com.runzo.userservice.repository;

import com.runzo.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    boolean existsByEmailOrPhone(String email, String phone);

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
