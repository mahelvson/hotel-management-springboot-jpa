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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/receptionists")
public class ReceptionistResource {

	@Autowired
	private ReceptionistFacade receptionistFacade;

	@GetMapping
	@Tag(name="Receptionist")
	@Operation(
			summary = "Search for all receptionists",
			description = "Return the list of all receptionists",
			tags = {"receptionist"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All receptionists listed"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Receptionist>> findAll() {
		List<Receptionist> receptionist = receptionistFacade.getAllReceptionists();
		return ResponseEntity.ok().body(receptionist);
	}

	@GetMapping(value = "/id/{id}")
	@Tag(name="Receptionist")
	@Operation(
			summary = "Search a receptionist by ID",
			description = "Return a single receptionists information passing its ID",
			tags = {"receptionist"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist info listed"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Receptionist> findById(@PathVariable Long id) {
		Receptionist receptionist = receptionistFacade.getReceptionistById(id);
		return ResponseEntity.ok().body(receptionist);
	}

	@GetMapping(value = "/email/{email}")
	@Tag(name="Receptionist")
	@Operation(
			summary = "Search receptionist by E-mail (username)",
			description = "Return the list of a single receptionist passing its E-mail",
			tags = {"receptionist"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist info listed"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Receptionist> findByEmail(@PathVariable String email) {
		Receptionist receptionist = receptionistFacade.getReceptionistByEmail(email);
		return ResponseEntity.ok().body(receptionist);
	}

	@PostMapping
	@Tag(name="Receptionist")
	@Operation(
			summary = "Register a new receptionist",
			description = "Attempt to register a new user of type {Receptionist} using request param (not request body as in client)",
			tags = {"receptionist, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Receptionist> createReceptionist(@RequestParam String name, @RequestParam String email,
			@RequestParam String password) {
		Receptionist receptionist = receptionistFacade.createReceptionist(name, email, password);
		return ResponseEntity.ok(receptionist);
	}

	@DeleteMapping(value = "/deleteByEmail")
	@Tag(name="Receptionist")
	@Operation(
			summary = "Remove a single receptionist by its E-mail",
			description = "Delete a receptionist from database",
			tags = {"receptionist, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Void> deleteReceptionistByEmail(@RequestParam String email) {
		receptionistFacade.deleteReceptionist(email);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/update/{id}")
	@Tag(name="Receptionist")
	@Operation(
			summary = "Update a receptionist registration",
			description = "Attempt to update the information of a receptionist.",
			tags = {"receptionist, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist info updated"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Receptionist> updateReceptionist(@PathVariable Long id, @RequestParam String name,
			@RequestParam String email, @RequestParam String password) {
		Receptionist updatedReceptionist = receptionistFacade.updateReceptionist(name, email, password);
		return ResponseEntity.ok(updatedReceptionist);
	}

}
