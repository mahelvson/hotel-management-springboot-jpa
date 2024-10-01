package com.hotel.manager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Client;
import com.hotel.manager.exceptions.UserAlreadyExistisException;
import com.hotel.manager.exceptions.UserNotFoundException;
import com.hotel.manager.interfaces.ClientServiceInterface;
import com.hotel.manager.repositories.ClientRepository;

@Service
public class ClientService implements ClientServiceInterface{
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findById(Long id) {
		return clientRepository.findClientById(id);
	}
	
	public Client findByEmail(String email) {
		return clientRepository.findClientByEmail(email);
	}
	
	@Override
	public Client createClient(Client client) {
		Client existingClient = this.findByEmail(client.getEmail());
		if (existingClient == null) {			
			return clientRepository.save(client);
		} else {
			throw new UserAlreadyExistisException("Client already exists");
		}
	}

	@Override
	public Client updateClient(Client client) {
		Client existingClient = this.findByEmail(client.getEmail());
		if (existingClient != null) {
			existingClient.setName(client.getName());
			existingClient.setEmail(client.getEmail());
			existingClient.setIsLoyaltyMember(client.getIsLoyaltyMember());
			return clientRepository.save(client);
		} else {
			throw new UserNotFoundException("Client not found");
		}
	}

}
