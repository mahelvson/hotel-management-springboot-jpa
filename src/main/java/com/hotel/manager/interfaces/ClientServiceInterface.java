package com.hotel.manager.interfaces;

import com.hotel.manager.entities.Client;

public interface ClientServiceInterface {
	
	Client findById(Long clientId);
	Client createClient(Client client);
	Client updateClient(Client client);
	

}
