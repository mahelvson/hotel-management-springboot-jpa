package com.hotel.manager.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.manager.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("SELECT b FROM Booking b JOIN b.rooms r WHERE r.id = :roomId AND "
			+ "(b.dateCheckIn <= :checkOut AND b.dateCheckOut >= :checkIn)")
	List<Booking> findConflictingBookings(@Param("roomId") Long roomId, @Param("checkIn") LocalDate checkIn,
			@Param("checkOut") LocalDate checkOut);

}
