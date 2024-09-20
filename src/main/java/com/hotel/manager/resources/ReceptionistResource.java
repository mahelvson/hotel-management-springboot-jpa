package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.facade.ReceptionistFacade;

@RestController
@RequestMapping(value = "/receptionists")
public class ReceptionistResource {

	@Autowired
	private ReceptionistFacade receptionistFacade;

	@GetMapping
	public ResponseEntity<List<Receptionist>> findAll() {
		List<Receptionist> receptionist = receptionistFacade.getAllReceptionists();
		return ResponseEntity.ok().body(receptionist);
	}

	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Receptionist> findById(@PathVariable Long id) {
		Receptionist receptionist = receptionistFacade.getReceptionistById(id);
		return ResponseEntity.ok().body(receptionist);
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<Receptionist> findByEmail(@PathVariable String email) {
		Receptionist receptionist = receptionistFacade.getReceptionistByEmail(email);
		return ResponseEntity.ok().body(receptionist);
	}

	@PostMapping
	public ResponseEntity<Receptionist> createReceptionist(@RequestParam String name, @RequestParam String email,
			@RequestParam String password) {
		Receptionist receptionist = receptionistFacade.createReceptionist(name, email, password);
		return ResponseEntity.ok(receptionist);
	}

	@DeleteMapping(value = "/deleteByEmail")
	public ResponseEntity<Void> deleteReceptionistByEmail(@RequestParam String email) {
		receptionistFacade.deleteReceptionist(email);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/update/{id}")
	public ResponseEntity<Receptionist> updateReceptionist(@PathVariable Long id, @RequestParam String name,
			@RequestParam String email, @RequestParam String password) {
		Receptionist updatedReceptionist = receptionistFacade.updateReceptionist(name, email, password);
		return ResponseEntity.ok(updatedReceptionist);
	}

}
