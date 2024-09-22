package com.hotel.manager.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.User;
import com.hotel.manager.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User login(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent() && user.get().getPassword().equals(password)) {
			return user.get();
		} else {
			throw new RuntimeException("Invalid email or password");
		}
	}
}
