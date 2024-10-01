package com.hotel.manager.interfaces;

import com.hotel.manager.entities.Receptionist;

public interface ReceptionistServiceInterface {

	Receptionist findById(Long receptionistId);
	Receptionist findByEmail(String email);
	void deleteReceptionist(Long receptionistId);
	Receptionist createReceptionist(Receptionist receptionist);
	Receptionist updateReceptionist(Receptionist receptinoist);
	
}
