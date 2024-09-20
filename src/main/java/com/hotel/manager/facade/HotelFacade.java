package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.services.HotelService;

@Component
public class HotelFacade {
	
	@Autowired
	private HotelService hotelService;
	
	public List<Hotel> getAllHotels() {
		return hotelService.findAll();
	}
	public void deleteHotel(Long id) {
		hotelService.deleteById(id);
	}
	public Hotel getHotelById(Long id) {
		return hotelService.findById(id);
	}
	public Hotel getHotelByName(String hotelName) {
		return hotelService.findByName(hotelName);
	}
	public Hotel getHotelByCity(String city) {
		return hotelService.findByCity(city);
	}
	public Hotel updateHotel(String hotelName, String city, Integer stars) {
		return hotelService.updateHotel(hotelName, city, stars);
	}
	public Hotel createHotel(String hotelName, String city, Integer stars) {
		return hotelService.createHotel(hotelName, city, stars);
	}
}
