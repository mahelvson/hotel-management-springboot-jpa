package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Client;
import com.hotel.manager.services.ClientService;

@Component
public class ClientFacade {
	
	@Autowired
	private ClientService clientService;
	
	public List<Client> getAllClients() {
		List<Client> clients = clientService.findAll();
		
		return clients;
	}
	
	public Client getClientById(Long id) {
		Client client = clientService.findById(id);
		return client;
	}
	public Client getClientByEmail(String email) {
		Client client = clientService.findByEmail(email);
		return client;
	}
	
	public Client createClient(String name, String email, String password) {
		Client client = clientService.createClient(name, email, password);
		return client;
	}
	
	public void deleteClient(String email) {
		clientService.deleteByEmail(email);
	}
	public Client updateClient(String name, String email, String password) {
		Client updatedClient = clientService.updateClient(name,  email,  password);
		return updatedClient;
	}

}
