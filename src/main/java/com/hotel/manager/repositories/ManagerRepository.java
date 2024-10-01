package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
	Manager findByEmail(String email);
	Manager findManagerById(Long id);
	void deleteByEmail(String email);
}
