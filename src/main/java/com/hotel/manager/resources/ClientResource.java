package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Client;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.ClientRepository;
import com.hotel.manager.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientRepository clientRepository;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> clients = clientService.findAll();
		return ResponseEntity.ok().body(clients);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = clientService.findById(id);
		return ResponseEntity.ok().body(client);
	}

	@PostMapping
	public ResponseEntity<Client> createClient(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam UserType userType) {
		Client client = clientService.createClient(email);
		client.setName(name);
		client.setPassword(password);
		client.setUserType(userType);
		clientRepository.save(client);
		
		return ResponseEntity.ok(client);
	}

	@GetMapping(value = "/{email}")
	public ResponseEntity<Client> findByEmail(@PathVariable String email) {
		Client client = clientService.findByEmail(email);
		return ResponseEntity.ok().body(client);
	}
}
