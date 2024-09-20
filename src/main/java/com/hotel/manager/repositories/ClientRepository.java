package com.hotel.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByEmail(String email);
	Optional<Client> deleteByEmail(String email);
}
