package com.hotel.manager.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.exceptions.RoomUnavailableException;
import com.hotel.manager.services.BookingService;

@RestController
@RequestMapping(value = "/bookings")
public class BookingResource {

	@Autowired
	private BookingService bookingService;

	@GetMapping
	public ResponseEntity<List<Booking>> findAll() {
		List<Booking> booking = bookingService.findAll();
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Booking> findById(@PathVariable Long id) {
		Booking booking = bookingService.findById(id);
		return ResponseEntity.ok().body(booking);
	}

	@PostMapping
	public ResponseEntity<Booking> createBooking(@RequestParam Long clientId, @RequestParam Long roomId,
			@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut, @RequestParam Double total) {
		Booking booking = bookingService.createBooking(clientId, roomId, checkIn, checkOut, total);
		return ResponseEntity.ok(booking);
	}

	@ExceptionHandler(RoomUnavailableException.class)
	public ResponseEntity<String> handleRoomUnavailableException(RoomUnavailableException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
}
