package com.hotel.manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	User findByName(String name);
	User findUserById(Long id);
	
	List<User> findByUserType(UserType userType);

}
