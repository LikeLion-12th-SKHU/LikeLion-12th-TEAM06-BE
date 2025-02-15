package com.likelion.plantication.user.domain.repository;

import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    void deleteByUser(User user);
    Optional<UserRefreshToken> findByUser_UserId(Long userId);
}