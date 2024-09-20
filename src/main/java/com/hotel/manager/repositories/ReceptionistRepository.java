package com.hotel.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long>{
	Optional<Receptionist> findByEmail(String email);
	Optional<Receptionist> deleteByEmail(String email);
}
