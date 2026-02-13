package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
		@Column(nullable = false, unique = true, length = 128)
	    private String tokenHash;
		
		@Column(nullable = false)
	    private LocalDateTime expiryAt;
		
		@Column(nullable = false)
		private Boolean revoked;
		
	    private String deviceId;
	    private String userAgent;

	    // Link to your user (choose id/email based on your User entity)
	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "user_id")  // if your User has Long id
	    private User user;
		
		
}
