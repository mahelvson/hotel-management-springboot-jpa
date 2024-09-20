package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.services.ReceptionistService;

@Component
public class ReceptionistFacade {
	
	@Autowired
	private ReceptionistService receptionistService;
	
	public List<Receptionist> getAllReceptionists() {
		List<Receptionist> receptionists = receptionistService.findAll();
		
		return receptionists;
	}
	
	public Receptionist getReceptionistById(Long id) {
		Receptionist receptionist = receptionistService.findById(id);
		return receptionist;
	}
	public Receptionist getReceptionistByEmail(String email) {
		Receptionist receptionist = receptionistService.findByEmail(email);
		return receptionist;
	}
	
	public Receptionist createReceptionist(String name, String email, String password) {
		Receptionist receptionist = receptionistService.createReceptionist(name, email, password);
		return receptionist;
	}
	
	public void deleteReceptionist(String email) {
		receptionistService.deleteByEmail(email);
	}
	public Receptionist updateReceptionist(String name, String email, String password) {
		Receptionist updatedReceptionist = receptionistService.updateReceptionist(name,  email,  password);
		return updatedReceptionist;
	}

}
