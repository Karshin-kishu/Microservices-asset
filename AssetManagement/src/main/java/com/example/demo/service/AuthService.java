package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RefreshRequestDTO;
import com.example.demo.entities.RefreshToken;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
	private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private UserRepository userRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(userDetails.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(
                user,
                request.getDeviceId(),
                request.getUserAgent()
        );

        return new LoginResponseDTO(accessToken, refreshToken);
    }
    
    public LoginResponseDTO refresh(RefreshRequestDTO request, String userAgent) {
        RefreshToken existingToken = refreshTokenService.validateRefreshToken(
                request.getRefreshToken(),
                request.getDeviceId()
        );

        String accessToken = jwtService.generateToken(existingToken.getUser().getEmail());
        String refreshToken = refreshTokenService.rotate(
                existingToken,
                request.getDeviceId(),
                userAgent
        );

        return new LoginResponseDTO(accessToken, refreshToken);
    }
}
