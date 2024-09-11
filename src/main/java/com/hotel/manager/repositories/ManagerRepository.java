package com.hotel.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
	Optional<Manager> findByEmail(String email);
}
