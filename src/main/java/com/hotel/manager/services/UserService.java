package com.hotel.manager.services;

import java.util.List;

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
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User findById(Long userId) {
		return userRepository.findUserById(userId);
	}
	
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public List<User> findByUserType(UserType userType) {
		return userRepository.findByUserType(userType);
	}
}
