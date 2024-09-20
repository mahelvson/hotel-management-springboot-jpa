package com.hotel.manager.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.services.BookingService;
import com.hotel.manager.services.RoomService;

@Component
public class BookingFacade {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired RoomService roomService;
	
	public List<Booking> getAllBooking() {
		List<Booking> bookings = bookingService.findAll();
		return bookings;
	}
	public Booking getById(Long id) {
		Booking booking = bookingService.findById(id);
		return booking;
	}
	public Booking createBooking(Long clientId, Long roomId, LocalDate checkIn, LocalDate checkOut, Double total, Integer guestsNumber) {
		Booking booking = bookingService.createBooking(clientId, roomId, checkIn, checkOut, total, guestsNumber);
		return booking;
	}
	public Booking updateBooking(Long bookingId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
		Booking updatedBooking = bookingService.updateBooking(bookingId, roomId, checkIn, checkOut);
		return updatedBooking;
	}
	public Booking confirmBooking(Long bookingId) {
		Booking confimatedBooking = bookingService.confirmBooking(bookingId);
		return confimatedBooking;
	}
	public void deleteBooking(Long id) {
		bookingService.deleteById(id);
	}
	
	

}
