package com.hotel.manager.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.hotel.manager.entities.Room;

public interface RoomServiceInterface {
	
	Room createRoom(Room room);
	Room updateRoom(Room room);
	void deleteRoom(Long roomId);
	Room findRoomById(Long roomId);
	List<Room> findAllRooms();
	List<Room> findAvailableRooms(Long hotelId, LocalDate dateCheckIn, LocalDate checkOut, Integer guestsNumber);
	Room findRoomByHotelIdAndRoomNumber(Long hotelId, Integer roomNumber);

}
