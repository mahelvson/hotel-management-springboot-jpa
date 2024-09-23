 package com.hotel.manager.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.dto.BookingDTO;
import com.hotel.manager.entities.Booking;
import com.hotel.manager.exceptions.RoomUnavailableException;
import com.hotel.manager.facade.BookingFacade;

@RestController
@RequestMapping(value = "/bookings")
public class BookingResource {
	
	@Autowired BookingFacade bookingFacade;

	@GetMapping
	public ResponseEntity<List<Booking>> findAll() {
		List<Booking> booking = bookingFacade.getAllBooking();
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Booking> findById(@PathVariable Long id) {
		Booking booking = bookingFacade.getById(id);
		return ResponseEntity.ok().body(booking);
	}

	@PostMapping
	//public ResponseEntity<Booking> createBooking(@RequestParam Long clientId, @RequestParam Long roomId,
	//		@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut, @RequestParam Double total, @RequestParam Integer guestsNumber) {
	public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingData) {
		Booking booking = bookingFacade.createBooking(bookingData.getClientId(), bookingData.getRoomId(), bookingData.getCheckIn(), bookingData.getCheckOut(), bookingData.getTotal(), bookingData.getGuestsNumber());
		return ResponseEntity.ok(booking);
	}
	
	@PatchMapping
	public ResponseEntity<Booking> updateBooking(@RequestParam Long bookingId, @RequestParam Long roomId,
			@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut) {
		Booking updatedBooking = bookingFacade.updateBooking(bookingId, roomId, checkIn, checkOut);
		return ResponseEntity.ok(updatedBooking);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteBooking(@RequestParam Long bookingId) {
		bookingFacade.deleteBooking(bookingId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/{id}/confirm")
	public ResponseEntity<Booking> confirmBooking(@RequestParam Long bookingId) {
		Booking confirmedBooking = bookingFacade.confirmBooking(bookingId);
		return ResponseEntity.ok(confirmedBooking);
	}
	
	@ExceptionHandler(RoomUnavailableException.class)
	public ResponseEntity<String> handleRoomUnavailableException(RoomUnavailableException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
}
