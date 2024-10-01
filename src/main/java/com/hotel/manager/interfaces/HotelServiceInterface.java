package com.hotel.manager.interfaces;

import com.hotel.manager.entities.Hotel;

public interface HotelServiceInterface {
	
	Hotel findById(Long hotelId);
	Hotel createHotel(Hotel hotel);
	void deleteHotel(Long hotelId);
	Hotel updateHotel(Hotel hotel);
	Hotel findHotelByCity(String city);

}
