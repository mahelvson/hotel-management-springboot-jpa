package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.services.BookingService;

@RestController
@RequestMapping(value="/bookings")
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
}
