package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.repositories.HotelRepository;

import jakarta.transaction.Transactional;

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

	public Hotel findByName(String hotelName) {
		Optional<Hotel> hotel = hotelRepository.findByHotelName(hotelName);
		return hotel.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}

	public Hotel findByCity(String city) {
		Optional<Hotel> hotel = hotelRepository.findByCity(city);
		return hotel.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}
	@Transactional
	public Hotel updateHotel(String hotelName, String city, Integer stars) {
		Hotel hotel = hotelRepository.findByHotelName(hotelName)
				.orElseThrow(() -> new RuntimeException("Hotel not fount"));
		hotel.setCity(city);
		hotel.setName(hotelName);
		hotel.setStars(stars);
		
		return this.save(hotel);
	}

	public Hotel createHotel(String hotelName, String city, Integer stars) {
		Hotel hotel = hotelRepository.findByHotelNameAndCity(hotelName, city)
				.orElse(new Hotel(null, hotelName, city, stars));
		
		return this.save(hotel);
	}
}
