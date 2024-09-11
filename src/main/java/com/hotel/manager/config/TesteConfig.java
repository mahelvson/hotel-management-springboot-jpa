package com.hotel.manager.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Manager;
import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.BookingRepository;
import com.hotel.manager.repositories.ClientRepository;
import com.hotel.manager.repositories.ManagerRepository;
import com.hotel.manager.repositories.ReceptionistRepository;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {
	
	@Autowired
	private ClientRepository clientRepository; // injecao de dependencia -> associa instancia
	@Autowired
	private ManagerRepository managerRepository; // injecao de dependencia -> associa instancia
	@Autowired
	private ReceptionistRepository receptionistRepository; // injecao de dependencia -> associa instancia
	@Autowired 
	private BookingRepository bookingRepository;
	@Override
	public void run(String... args) throws Exception {

		Client cli1 = new Client(null, "Pedro", "pedro@gmail.com", "12345", UserType.CLIENT);
		Client cli2 = new Client(null, "Joao", "joao@gmail.com", "12345", UserType.CLIENT);
		clientRepository.saveAll(Arrays.asList(cli1, cli2));
		//Booking book1 = new Booking(null, cli1, "2023-09-10T14:00:00Z", "2023-09-12T14:00:00Z", BookingStatus.CONFIRMED);
		
		Manager man1 = new Manager(null, "Thais", "thais@gmail.com", "12345", UserType.MANAGER);			
		managerRepository.saveAll(Arrays.asList(man1));	
		
		Receptionist rec1 = new Receptionist(null, "Jose", "jose@gmail.com", "12345", UserType.RECEPTIONIST);
		Receptionist rec2 = new Receptionist(null, "Arthur", "arthur@gmail.com", "12345", UserType.RECEPTIONIST);		
		receptionistRepository.saveAll(Arrays.asList(rec1, rec2));	
		
	}	

}
