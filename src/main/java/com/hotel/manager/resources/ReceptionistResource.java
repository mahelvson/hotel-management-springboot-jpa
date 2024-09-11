package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.services.ReceptionistService;

@RestController
@RequestMapping(value="/receptionists")
public class ReceptionistResource {
	
	@Autowired
	private ReceptionistService receptionistService;
	
	@GetMapping
	public ResponseEntity<List<Receptionist>> findAll() {
		List<Receptionist> receptionist = receptionistService.findAll();		
		return ResponseEntity.ok().body(receptionist);
	}
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<Receptionist> findById(@PathVariable Long id) {
		Receptionist receptionist = receptionistService.findById(id);
        return ResponseEntity.ok().body(receptionist);
    }
	@GetMapping(value = "/{email}")
    public ResponseEntity<Receptionist> findById(@PathVariable String email) {
		Receptionist receptionist = receptionistService.findByEmail(email);
        return ResponseEntity.ok().body(receptionist);
    }	
}
