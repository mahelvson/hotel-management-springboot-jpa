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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/bookings")
public class BookingResource {
	
	@Autowired BookingFacade bookingFacade;

	@GetMapping
	@Tag(name="Booking")
	@Operation(
			summary = "Search for all bookings",
			description = "Return the list of all bookings in the system",
			tags = {"booking, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All bookings listed"),
					@ApiResponse(responseCode = "404", description = "Bookings not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Booking>> findAll() {
		List<Booking> booking = bookingFacade.getAllBooking();
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping(value = "/id/{id}")
	@Tag(name="Booking")
	@Operation(
			summary = "Search a booking by ID",
			description = "Return a single booking information passing its ID",
			tags = {"booking, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking info listed"),
					@ApiResponse(responseCode = "404", description = "Booking not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Booking> findById(@PathVariable Long id) {
		Booking booking = bookingFacade.getById(id);
		return ResponseEntity.ok().body(booking);
	}

	@PostMapping
	//public ResponseEntity<Booking> createBooking(@RequestParam Long clientId, @RequestParam Long roomId,
	//		@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut, @RequestParam Double total, @RequestParam Integer guestsNumber) {
	@Tag(name="Booking")
	@Operation(
			summary = "Register a new booking",
			description = "Attempt to register a new booking",
			tags = {"booking, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingData) {
		Booking booking = bookingFacade.createBooking(bookingData.getClientId(), bookingData.getRoomId(), bookingData.getCheckIn(), bookingData.getCheckOut(), bookingData.getTotal(), bookingData.getGuestsNumber());
		return ResponseEntity.ok(booking);
	}
	
	@PatchMapping
	@Tag(name="Booking")
	@Operation(
			summary = "Update a booking",
			description = "Attempt to update the information of a booking.",
			tags = {"booking, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking info updated"),
					@ApiResponse(responseCode = "404", description = "Booking not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Booking> updateBooking(@RequestParam Long bookingId, @RequestParam Long roomId,
			@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut) {
		Booking updatedBooking = bookingFacade.updateBooking(bookingId, roomId, checkIn, checkOut);
		return ResponseEntity.ok(updatedBooking);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	@Tag(name="Booking")
	@Operation(
			summary = "Remove a single booking by its ID",
			description = "Delete a booking from database",
			tags = {"booking, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Void> deleteBooking(@RequestParam Long bookingId) {
		bookingFacade.deleteBooking(bookingId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/{id}/confirm")
	@Tag(name="Booking")
	@Operation(
			summary = "Confirm a booking.",
			description = "Update the booking to a `status: confirmed` in the database",
			tags = {"booking, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking confirmed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Booking> confirmBooking(@RequestParam Long bookingId) {
		Booking confirmedBooking = bookingFacade.confirmBooking(bookingId);
		return ResponseEntity.ok(confirmedBooking);
	}
	
	@ExceptionHandler(RoomUnavailableException.class)
	public ResponseEntity<String> handleRoomUnavailableException(RoomUnavailableException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@GetMapping("/user/{userId}")
	@Tag(name="Booking")
	@Operation(
			summary = "Search booking for a client",
			description = "Return a list of bookings already created by client id",
			tags = {"booking, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Booking info updated"),
					@ApiResponse(responseCode = "404", description = "Booking not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingFacade.findBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
}
