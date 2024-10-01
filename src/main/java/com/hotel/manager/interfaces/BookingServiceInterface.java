package com.hotel.manager.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;

@Service
public interface BookingServiceInterface {

	Booking createBooking(Booking booking);
	Booking updateBooking(Booking booking);
	void deleteBooking(Long bookingId);
	Optional<Booking> findBookingById(Long bookingId);
	List<Booking> findConflictBookings(Long roomId, LocalDate checkIn, LocalDate checkOut);
	List<Booking> findBookingsByClientId(Long clientId);
	List<Booking> findAllBookings();
	void ConfirmPayment(Booking booking);
}
