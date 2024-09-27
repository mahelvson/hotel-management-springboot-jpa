package com.hotel.manager.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.manager.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	@Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND r.capacity >= :guestsNumber AND r.id NOT IN "
		       + "(SELECT b.room.id FROM Booking b WHERE "
		       + "b.dateCheckIn <= :checkOut AND b.dateCheckOut >= :checkIn)")
		List<Room> findAvailableRoomsByHotelAndDatesAndCapacity(@Param("hotelId") Long hotelId, 
		                                                        @Param("checkIn") LocalDate checkIn, 
		                                                        @Param("checkOut") LocalDate checkOut, 
		                                                        @Param("guestsNumber") Integer guestsNumber);

		void deleteById(Long id);

}
