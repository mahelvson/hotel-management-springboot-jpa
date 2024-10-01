package com.hotel.manager.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.repositories.BookingRepository;

@Service
public interface BookingServiceInterface {

	Booking createBooking(Booking booking);
	Booking updateBooking(Booking booking);
	void deleteBooking(Long bookingId);
	Booking findBookingById(Long bookingId);
	List<Booking> findConflictBookings(Long roomId, LocalDate checkIn, LocalDate checkOut);
	List<Booking> findBookingsByClientId(Long clientId);
	List<Booking> findAllBookings();
	void ConfirmPayment(Booking booking);
	void setBookingRepository(BookingRepository bookingRepository);
}
