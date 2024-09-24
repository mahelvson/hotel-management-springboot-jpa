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

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	
	@Autowired
	private ClientFacade clientFacade;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> clients = clientFacade.getAllClients();
		return ResponseEntity.ok().body(clients);
	}

	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = clientFacade.getClientById(id);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<Client> findByEmail(@PathVariable String email) {
		Client client = clientFacade.getClientByEmail(email);
		return ResponseEntity.ok().body(client);
	}

	@PostMapping
	//public ResponseEntity<Client> createClient(@RequestParam String name, @RequestParam String email,
	//		@RequestParam String password) {
	public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
		Client client = clientFacade.createClient(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getPassword());
		return ResponseEntity.ok(client);
	}

	@DeleteMapping(value = "/delete/{email}")
	public ResponseEntity<Void> deleteClientByEmail(@RequestParam String email) {
		clientFacade.deleteClient(email);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/update/{id}")
	public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestParam String name,
			@RequestParam String email, @RequestParam String password) {
		Client updatedClient = clientFacade.updateClient(name, email, password);
		return ResponseEntity.ok(updatedClient);
	}
	
	
}
