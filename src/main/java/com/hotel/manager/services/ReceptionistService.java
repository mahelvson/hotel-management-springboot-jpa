package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.ReceptionistRepository;

import jakarta.transaction.Transactional;

@Service
public class ReceptionistService {
	@Autowired
	private ReceptionistRepository receptionistRepository;
	
	public List<Receptionist> findAll() {
		return receptionistRepository.findAll();
	}
	
	public Receptionist findById(Long id) {
		Optional<Receptionist> obj = receptionistRepository.findById(id);
		return obj.orElseThrow(()-> new RuntimeException("Receptionist not found"));
	}
	
	public Receptionist findByEmail(String email) {
		Optional<Receptionist> obj = receptionistRepository.findByEmail(email);
		return obj.orElseThrow(()-> new RuntimeException("Receptionist e-mail not found"));
	}
	
	public Receptionist save(Receptionist receptionist) {
		return receptionistRepository.save(receptionist);
	}
	
	public void deleteById(Long id) {
		receptionistRepository.deleteById(id);
	}
	
	public Receptionist createReceptionist(String name, String email, String password) {
		Receptionist receptionist = receptionistRepository.findByEmail(email).orElse(new Receptionist(null, "Default Name", email, "12345", UserType.CLIENT ));
		receptionist.setName(name);
		receptionist.setPassword(password);
		receptionist.setUserType(UserType.RECEPTIONIST);
		return this.save(receptionist);
		
	}
	@Transactional
	public void deleteByEmail(String email) {
		Receptionist receptionist = receptionistRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Receptionist " + email + " not found!"));
		receptionistRepository.deleteByEmail(receptionist.getEmail());
	}
	@Transactional
	public Receptionist updateReceptionist(String name, String email, String password) {
		Receptionist receptionist = receptionistRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("Receptionist not found"));
		receptionist.setEmail(email);
		receptionist.setName(name);
		receptionist.setPassword(password);
		
		return this.save(receptionist);
	}

}
