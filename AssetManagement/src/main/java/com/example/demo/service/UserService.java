package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String register(String email, String password, String name) {
		if(userRepository.findByEmail(email).isPresent()) {
			
			return "User already exists";
		}
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setName(name);
		user.setEnabled(true);
		
		Role role  = roleRepository.findByRoleName("USER")
				.orElseThrow(() -> new RuntimeException("Default role not found"));
		
		user.setRoles(List.of(role));
		
		userRepository.save(user);
		System.out.println("User registered: " + email);
		return "User registered successfully";
	}
	
	
	
}
