package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.interfaces.HotelServiceInterface;
import com.hotel.manager.repositories.HotelRepository;

@Service
public class HotelService implements HotelServiceInterface {
	@Autowired
	private HotelRepository hotelRepository;

	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}
	@Override
	public Hotel findById(Long id) {
		Optional<Hotel> obj = hotelRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}
	@Override
	public void deleteHotel(Long id) {
		hotelRepository.deleteById(id);
	}
	public Hotel findByName(String hotelName) {
		return hotelRepository.findByHotelName(hotelName);
	}
	@Override
	public Hotel findHotelByCity(String city) {
		Optional<Hotel> hotel = hotelRepository.findByCity(city);
		return hotel.orElseThrow(() -> new RuntimeException("Hotel not found"));
	}
	@Override
	public Hotel updateHotel(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	@Override
	public Hotel createHotel(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
}
