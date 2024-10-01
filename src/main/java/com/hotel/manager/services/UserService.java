package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.interfaces.UserServiceInterface;
import com.hotel.manager.repositories.UserRepository;

@Service
public class UserService implements UserServiceInterface {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	@Override
	public User findById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		if (user != null) {
			userRepository.deleteById(userId);
		} else {
			throw new RuntimeException("User not found");
		}
	}

	@Override
	public List<User> findByUserType(UserType userType) {
		return userRepository.findByUserType(userType);
	}
}
