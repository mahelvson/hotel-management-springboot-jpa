package com.hotel.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	Hotel findByHotelName(String hotelName);
	Optional<Hotel> findByCity(String city);
	Optional<Hotel> findByHotelNameAndCity(String hotelName, String city);

}
