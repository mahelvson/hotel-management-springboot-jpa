package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.dto.ClientDTO;
import com.hotel.manager.entities.Client;
import com.hotel.manager.facade.ClientFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	
	@Autowired
	private ClientFacade clientFacade;

	@GetMapping
	@Tag(name="Client")
	@Operation(
			summary = "Search for all clients",
			description = "Return the list of all clients",
			tags = {"clients, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All clients listed"),
					@ApiResponse(responseCode = "404", description = "Clients not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Client>> findAll() {
		List<Client> clients = clientFacade.getAllClients();
		return ResponseEntity.ok().body(clients);
	}

	@GetMapping(value = "/id/{id}")
	@Tag(name="Client")
	@Operation(
			summary = "Search a client by ID",
			description = "Return a single client information passing its ID",
			tags = {"clients, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Client info listed"),
					@ApiResponse(responseCode = "404", description = "Client not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = clientFacade.getClientById(id);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/email/{email}")
	@Tag(name="Client")
	@Operation(
			summary = "List clients by E-mail (username)",
			description = "Return the list of a single client passing its E-mail",
			tags = {"clients"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Client info listed"),
					@ApiResponse(responseCode = "404", description = "Client not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Client> findByEmail(@PathVariable String email) {
		Client client = clientFacade.getClientByEmail(email);
		return ResponseEntity.ok().body(client);
	}

	@PostMapping
	//public ResponseEntity<Client> createClient(@RequestParam String name, @RequestParam String email,
	//		@RequestParam String password) {
	@Tag(name="Client")
	@Operation(
			summary = "Register a client DTO",
			description = "Attempt to register a new user of type {Client}",
			tags = {"clients, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Client registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
		Client client = clientFacade.createClient(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getPassword());
		return ResponseEntity.ok(client);
	}
	
	@Operation(
			summary = "Remove a single client by its E-mail",
			description = "Delete a client from database",
			tags = {"clients, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Client removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	@Tag(name="Client")
	@DeleteMapping(value = "/deleteByEmail")
	public ResponseEntity<Void> deleteClientByEmail(@RequestParam String email) {
		clientFacade.deleteClient(email);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/update/{id}")
	@Tag(name="Client")
	@Operation(
			summary = "Update a client registration",
			description = "Attempt to update the register of a client.",
			tags = {"clients, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Client info updated"),
					@ApiResponse(responseCode = "404", description = "Client not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestParam String name,
			@RequestParam String email, @RequestParam String password) {
		Client updatedClient = clientFacade.updateClient(name, email, password);
		return ResponseEntity.ok(updatedClient);
	}
	
	
}
