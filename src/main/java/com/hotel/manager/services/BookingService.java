package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.repositories.BookingRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

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
}
