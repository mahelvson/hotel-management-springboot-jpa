package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	Hotel findByHotelName(String hotelName);
	Hotel findByCity(String city);
	Hotel findHotelById(Long id);
	Hotel findByHotelNameAndCity(String hotelName, String city);

}
