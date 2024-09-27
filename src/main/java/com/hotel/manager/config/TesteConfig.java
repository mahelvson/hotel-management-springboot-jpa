package com.hotel.manager.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Hotel;
import com.hotel.manager.entities.Manager;
import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.entities.Room;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.ClientRepository;
import com.hotel.manager.repositories.HotelRepository;
import com.hotel.manager.repositories.ManagerRepository;
import com.hotel.manager.repositories.ReceptionistRepository;
import com.hotel.manager.repositories.RoomRepository;
import com.hotel.manager.services.BookingService;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository; 
	@Autowired
	private ManagerRepository managerRepository; 
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private BookingService bookingService;

	@Override
	public void run(String... args) throws Exception {
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode("12345");

		Client cli1 = new Client(null, "Pedro", "pedro@gmail.com", encodedPassword, UserType.CLIENT);
		Client cli2 = new Client(null, "Joao", "joao@gmail.com", encodedPassword, UserType.CLIENT);
		
		clientRepository.saveAll(Arrays.asList(cli1, cli2));

		Hotel hotel1 = new Hotel(null, "Salinas Maragogi", "Maragogi", 5);
		Hotel hotel2 = new Hotel(null, "Salinas Maceió", "Maceió", 5);

		hotelRepository.save(hotel1);
		hotelRepository.save(hotel2);

		Room room1 = new Room(null, 2, 1, 220.20, hotel1, 100);
		Room room2 = new Room(null, 0, 1, 233.20, hotel1, 200);
		Room room3 = new Room(null, 2, 1, 240.20, hotel2, 1234);
		Room room4 = new Room(null, 2, 0, 224.20, hotel2, 4321);

		roomRepository.saveAll(Arrays.asList(room1, room2, room3, room4));

		Manager man1 = new Manager(null, "Thais", "thais@gmail.com", encodedPassword, UserType.MANAGER);
		managerRepository.saveAll(Arrays.asList(man1));

		Receptionist rec1 = new Receptionist(null, "Jose", "jose@gmail.com", encodedPassword, UserType.RECEPTIONIST);
		Receptionist rec2 = new Receptionist(null, "Arthur", "arthur@gmail.com", encodedPassword, UserType.RECEPTIONIST);
		receptionistRepository.saveAll(Arrays.asList(rec1, rec2));
		bookingService.createBooking(cli1.getId(), room1.getId(), LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 15), 4);
		bookingService.createBooking(cli1.getId(), room1.getId(), LocalDate.of(2023, 7, 10), LocalDate.of(2023, 7, 15), 4);
		bookingService.createBooking(cli1.getId(), room1.getId(), LocalDate.of(2022, 6, 10), LocalDate.of(2022, 6, 15), 4);
		bookingService.createBooking(cli1.getId(), room1.getId(), LocalDate.of(2021, 5, 10), LocalDate.of(2021, 5, 15), 4);
		bookingService.createBooking(cli1.getId(), room1.getId(), LocalDate.of(2020, 4, 10), LocalDate.of(2020, 4, 15), 4);
		bookingService.createBooking(cli1.getId(), room1.getId(), LocalDate.of(2019, 3, 10), LocalDate.of(2019, 3, 15), 4);
	}
}
