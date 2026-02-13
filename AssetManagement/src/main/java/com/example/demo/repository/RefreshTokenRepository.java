package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.RefreshToken;
import com.example.demo.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByTokenHashAndRevokedFalse(String tokenHash);
	Long deleteByUser(User user);
}
