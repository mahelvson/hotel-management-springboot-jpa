package com.hotel.manager.interfaces;

import java.util.List;

import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;

public interface UserServiceInterface {
	List<User> findByUserType(UserType userType);
	User findById(Long userId);
	User findByEmail(String email);
	void deleteUser(Long userId);
}
