package com.hotel.manager.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.interfaces.BookingServiceInterface;
import com.hotel.manager.repositories.BookingRepository;

@Service
public class BookingService implements BookingServiceInterface {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public List<Booking> findConflictBookings(Long roomId, LocalDate checkIn, LocalDate checkOut) {
		return bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
	}
	
	@Override
	public Booking updateBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public void deleteBooking(Long bookingId) {
		bookingRepository.deleteById(bookingId);
	}

	@Override
	public Booking findBookingById(Long bookingId) {
		return bookingRepository.findBookingById(bookingId);
	}

	@Override
	public List<Booking> findBookingsByClientId(Long clientId) {
		return bookingRepository.findBookingsByClientId(clientId);
	}

	@Override
	public List<Booking> findAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public void ConfirmPayment(Booking booking) {
		bookingRepository.save(booking);
	}
	
	public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
}
