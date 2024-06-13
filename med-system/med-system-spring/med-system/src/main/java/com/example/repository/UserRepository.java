package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);
    boolean existsByUserNameOrEmail(String username, String email);
    Optional<User> findByUserNameOrEmail(String username, String email);
}
