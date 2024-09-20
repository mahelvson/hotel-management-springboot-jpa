package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Client;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		return obj.orElseThrow(()-> new RuntimeException("Client not found"));
	}
	
	public Client findByEmail(String email) {
		Optional<Client> obj = clientRepository.findByEmail(email);
		return obj.orElseThrow(()-> new RuntimeException("Client e-mail not found"));
	}
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
	
	public Client createClient(String name, String email, String password) {
		Client client = clientRepository.findByEmail(email).orElse(new Client(null, "Default Name", email, "12345", UserType.CLIENT ));
		client.setName(name);
		client.setPassword(password);
		client.setUserType(UserType.CLIENT);
		return this.save(client);
		
	}
	@Transactional
	public void deleteByEmail(String email) {
		Client client = clientRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Client " + email + " not found!"));
		clientRepository.deleteByEmail(client.getEmail());
	}
	@Transactional
	public Client updateClient(String name, String email, String password) {
		Client client = clientRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("Client not found"));
		client.setEmail(email);
		client.setName(name);
		client.setPassword(password);
		
		return this.save(client);
	}

}
