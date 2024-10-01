package com.hotel.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.exceptions.UserAlreadyExistisException;
import com.hotel.manager.exceptions.UserNotFoundException;
import com.hotel.manager.interfaces.ReceptionistServiceInterface;
import com.hotel.manager.repositories.ReceptionistRepository;

@Service
public class ReceptionistService implements ReceptionistServiceInterface {
	
	@Autowired
	private ReceptionistRepository receptionistRepository;

	@Override
	public Receptionist findById(Long id) {
		return receptionistRepository.findReceptionistById(id);
	}

	@Override
	public void deleteReceptionist(Long receptionistId) {
		receptionistRepository.deleteById(receptionistId);
	}

	@Override
	public Receptionist createReceptionist(Receptionist receptionist) {
		Receptionist existingReceptionist = this.findByEmail(receptionist.getEmail());
		if (existingReceptionist == null) {
			return receptionistRepository.save(receptionist);
		} else {
			throw new UserAlreadyExistisException("Receptionist already exists");
		}
	}

	@Override
	public Receptionist updateReceptionist(Receptionist receptionist) {
		Receptionist existingReceptionist = this.findByEmail(receptionist.getEmail());
		if (existingReceptionist != null) {
			existingReceptionist.setName(receptionist.getName());
			existingReceptionist.setEmail(receptionist.getEmail());
			existingReceptionist.setCanGrantDiscount(receptionist.isCanGrantDiscount());
			return receptionistRepository.save(receptionist);
		} else {
			throw new UserNotFoundException("Receptionist not found");
		}
	}

	@Override
	public Receptionist findByEmail(String email) {
		return receptionistRepository.findByEmail(email);
	}
}
