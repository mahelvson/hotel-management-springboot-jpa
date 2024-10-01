package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long>{
	Receptionist findByEmail(String email);
	Receptionist findReceptionistById(Long id);
	void deleteByEmail(String email);
}
