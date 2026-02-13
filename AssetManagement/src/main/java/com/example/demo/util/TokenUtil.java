package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

		private final SecureRandom secureRandom = new SecureRandom();
		
		public String generateToken() {
		    byte[] randomBytes = new byte[64];
		    secureRandom.nextBytes(randomBytes);
		    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
		}
		
		public String sha256(String raw) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
			    byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
			    return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
			} catch (Exception e) {
				throw new RuntimeException("Error hashing token", e);
			}
		}
}
