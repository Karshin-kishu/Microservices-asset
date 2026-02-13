package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.IntrospectResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RefreshRequestDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
		System.out.println("Received login request: " + loginDTO.getEmail()); 	
		return ResponseEntity.ok(authService.login(loginDTO));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponseDTO> refresh(
			@RequestBody RefreshRequestDTO refreshDTO,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		return ResponseEntity.ok(authService.refresh(refreshDTO, userAgent));
	}
	
	
	public String login() {
		return "Login endpoint";
	}
	
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
	
    @PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
		System.out.println("Received registration request: " + registerDTO.getEmail()); 	
    	userService.register(registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getName());
		return ResponseEntity.ok("User registered successfully");
	}
    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponseDTO> introspect(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(new IntrospectResponseDTO(false, null, null));
        }

        String token = authHeader.substring(7);

        try {
            // 1️⃣ Validate token
            String email = jwtService.extractUsername(token);

            if (!jwtService.validateToken(token, email)) {
                return ResponseEntity.ok(new IntrospectResponseDTO(false, null, null));
            }

            // 2️⃣ Load user from DB
            User user = userRepository.findByEmail(email)
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.ok(new IntrospectResponseDTO(false, null, null));
            }

            // 3️⃣ Extract roles
            List<String> roles = user.getRoles()
                    .stream()
                    .map(role -> role.getRoleName())
                    .toList();

            // 4️⃣ Return valid response
            return ResponseEntity.ok(
                    new IntrospectResponseDTO(true, email, roles)
            );

        } catch (Exception ex) {
            return ResponseEntity.ok(new IntrospectResponseDTO(false, null, null));
        }
    }
}
