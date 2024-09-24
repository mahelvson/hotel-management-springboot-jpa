package com.hotel.manager.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Room;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.exceptions.ConfirmedBookingException;
import com.hotel.manager.exceptions.RoomUnavailableException;
import com.hotel.manager.repositories.BookingRepository;
import com.hotel.manager.repositories.ClientRepository;
import com.hotel.manager.repositories.RoomRepository;

import jakarta.transaction.Transactional;

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
	
	public long daysBetween(LocalDate checkIn, LocalDate checkOut) {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
	}
	
	@Transactional
	public Booking createBooking(Long clientId, Long roomId, LocalDate checkIn, LocalDate checkOut, Double total, Integer guestsNumber) {
		Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
		List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
		if (!conflictingBookings.isEmpty()) {
			throw new RoomUnavailableException("Room is already booked for the selected dates");
		}
		long nDays = this.daysBetween(checkIn, checkOut);
		double daily = room.getDiaryValue();
		double totalValue = nDays * daily;
		Booking booking = new Booking(null, checkIn, checkOut, client, BookingStatus.CONFIRMED, totalValue, room, guestsNumber);
		
		return bookingRepository.save(booking);
	}
	
	@Transactional
	public Booking updateBooking(Long bookingId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
		if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
			throw new ConfirmedBookingException("Booking already confirmed. It is not possible to update.");
		}
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
		List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
		if (!conflictingBookings.isEmpty()) {
			throw new RuntimeException("Room is already booked for the selected dates");
		}
		
		booking.setDateCheckIn(checkIn);
		booking.setDateCheckOut(checkOut);
		booking.setRoom(room);
		long nDays = this.daysBetween(checkIn, checkOut);
		double daily = room.getDiaryValue();
		double totalValue = nDays * daily;
		booking.setTotal(totalValue);
		
		return bookingRepository.save(booking);
	}
	
	@Transactional
	public Booking confirmBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
		booking.setBookingStatus(BookingStatus.CONFIRMED);
		return bookingRepository.save(booking);
	}

	public List<Booking> findBookingsByUserId(Long userId) {
		return bookingRepository.findByClientId(userId);
	}
}
