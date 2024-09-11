package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.repositories.HotelRepository;

@Service
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;

	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}

	public Hotel findById(Long id) {
		Optional<Hotel> obj = hotelRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}

	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	public void deleteById(Long id) {
		hotelRepository.deleteById(id);
	}

	public Hotel findByName(String name) {
		Optional<Hotel> obj = hotelRepository.findByName(name);
		return obj.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}

	public Hotel findByCity(String city) {
		Optional<Hotel> obj = hotelRepository.findByCity(city);
		return obj.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}
}
