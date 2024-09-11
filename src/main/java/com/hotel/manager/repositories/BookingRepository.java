package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
