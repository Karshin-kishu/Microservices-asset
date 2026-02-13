package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
		 // Flow: JWT filter calls → load user from DB
	 Optional<User> findByEmail(String email);
}
