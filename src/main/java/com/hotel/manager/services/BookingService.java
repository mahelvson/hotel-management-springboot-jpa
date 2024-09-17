package com.hotel.manager.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Room;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.repositories.BookingRepository;
import com.hotel.manager.repositories.ClientRepository;
import com.hotel.manager.repositories.RoomRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private ClientRepository clientRepository;

	public List<Booking> findAll() {
		return bookingRepository.findAll();
	}

	public Booking findById(Long id) {
		Optional<Booking> obj = bookingRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Booking not found"));
	}

	public Booking save(Booking booking) {
		return bookingRepository.save(booking);
	}

	public void deleteById(Long id) {
		bookingRepository.deleteById(id);
	}

	public Booking createBooking(Long clientId, Long roomId, LocalDate checkIn, LocalDate checkOut, Double total) {
		Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
		List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
		if (!conflictingBookings.isEmpty()) {
			throw new RuntimeException("Room is already booked for the selected dates");
		}

		Booking booking = new Booking(null, checkIn, checkOut, client, BookingStatus.CONFIRMED, total, List.of(room));

		return bookingRepository.save(booking);
	}
}
