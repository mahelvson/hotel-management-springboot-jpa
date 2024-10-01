package com.hotel.manager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);
	
	List<User> findByUserType(UserType userType);

}
