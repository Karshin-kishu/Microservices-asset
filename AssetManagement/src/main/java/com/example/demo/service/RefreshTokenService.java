package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.RefreshToken;
import com.example.demo.entities.User;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.util.TokenUtil;

@Service
public class RefreshTokenService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private final long refreshDays = 7; // 7 days validity for refresh tokens
	
	public String createRefreshToken(User user, String deviceId, String userAgent) {
		String rawToken = tokenUtil.generateToken(); // generate a random token
		String tokenHash = tokenUtil.sha256(rawToken);
		
		RefreshToken rtk = RefreshToken.builder()
						   .tokenHash(tokenHash)
						   .expiryAt(LocalDateTime.now().plusDays(refreshDays))
						   .revoked(false)
						   .deviceId(deviceId)
						   .userAgent(userAgent)
						   .user(user)
						   .build();
		
		refreshTokenRepository.save(rtk);
		
		// Create and save the RefreshToken entity (omitted for brevity)
		// Set expiryAt to now + refreshDays, revoked to false, and link to user by userId
		
		return rawToken; // return the raw token to the client
	}
	
	public RefreshToken validateRefreshToken(String rawToken, String deviceId) {
		String tokenHash = tokenUtil.sha256(rawToken);
		RefreshToken rtk = refreshTokenRepository.findByTokenHashAndRevokedFalse(tokenHash)
						.orElseThrow(() -> new RuntimeException("Invalid refresh token"));
		
		if (!Objects.equals(rtk.getDeviceId(), deviceId)) {
			rtk.setRevoked(true);
			refreshTokenRepository.save(rtk);
			throw new RuntimeException("Invalid refresh token device");
		}
		
		if (rtk.getExpiryAt().isBefore(LocalDateTime.now())) {
			rtk.setRevoked(true);
			refreshTokenRepository.save(rtk);
			throw new RuntimeException("Refresh token expired");
		}
		
		return rtk; // valid token, return the entity for further processing
	}
	
	public String rotate(RefreshToken old, String deviceId, String userAgent) {
        old.setRevoked(true);
        refreshTokenRepository.save(old);

        return createRefreshToken(old.getUser(), deviceId, userAgent);
    }

    public void revokeAll(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
