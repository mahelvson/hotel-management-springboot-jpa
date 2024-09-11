package com.hotel.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	Optional<Hotel> findByName(String name);
	Optional<Hotel> findByCity(String city);

}
