package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Client;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.ClientRepository;

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
	
	public Client createClient(String email) {
		Client client = clientRepository.findByEmail(email).orElse(new Client(null, "Default Name", email, "12345", UserType.CLIENT ));
		return client;
	}
}
