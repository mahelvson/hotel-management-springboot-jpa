package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.dto.HotelDTO;
import com.hotel.manager.entities.Hotel;
import com.hotel.manager.exceptions.HotelAlreadyExistsException;
import com.hotel.manager.services.HotelService;

@Component
public class HotelFacade {
	
	@Autowired
	private HotelService hotelService;
	
	public List<Hotel> getAllHotels() {
		return hotelService.findAll();
	}
	public void deleteHotel(Long id) {
		hotelService.deleteHotel(id);
	}
	public Hotel findHotelById(Long id) {
		return hotelService.findById(id);
	}
	public Hotel getHotelByName(String hotelName) {
		return hotelService.findByName(hotelName);
	}

	public Hotel updateHotel(Long hotelId, HotelDTO hotelDTO) {
		Hotel hotel = hotelService.findById(hotelId);
		hotel.setName(hotelDTO.getHotelName() == null ? hotel.getName() : hotelDTO.getHotelName());
		hotel.setStars(hotelDTO.getStars() == null ? hotel.getStars() : hotelDTO.getStars());
		hotel.setCity(hotelDTO.getCity() == null ? hotel.getCity() : hotelDTO.getCity());
		
		return hotelService.updateHotel(hotel);
	}
	public Hotel createHotel(HotelDTO hotelDTO) {
		Hotel existingHotel = hotelService.findByName(hotelDTO.getHotelName());
		
		if (existingHotel != null) {
			throw new HotelAlreadyExistsException("Hotel already existis");
		}
		Hotel newHotel = new Hotel(hotelDTO.getHotelName(), hotelDTO.getCity(), hotelDTO.getStars());
		return hotelService.createHotel(newHotel);
	}
	public Hotel getHotelByCity(String city) {
		return hotelService.findHotelByCity(city);
	}
}
