package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Client findClientById(Long id);
	Client findClientByEmail(String email);
	void deleteByEmail(String email);
}
