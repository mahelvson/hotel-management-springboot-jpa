package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.repositories.ReceptionistRepository;

@Service
public class ReceptionistService {

	@Autowired
	private ReceptionistRepository receptionistRepository;

	public List<Receptionist> findAll() {
		return receptionistRepository.findAll();
	}

	public Receptionist findById(Long id) {
		Optional<Receptionist> obj = receptionistRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Receptionist not found"));
	}

	public Receptionist findByEmail(String email) {
		Optional<Receptionist> obj = receptionistRepository.findByEmail(email);
		return obj.orElseThrow(() -> new RuntimeException("Receptionist e-mail not found"));
	}

	public Receptionist save(Receptionist receptionist) {
		return receptionistRepository.save(receptionist);
	}

	public void deleteById(Long id) {
		receptionistRepository.deleteById(id);
	}
}
