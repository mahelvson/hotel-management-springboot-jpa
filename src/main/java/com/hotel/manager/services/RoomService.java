package com.hotel.manager.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Room;
import com.hotel.manager.interfaces.RoomServiceInterface;
import com.hotel.manager.repositories.RoomRepository;

@Service
public class RoomService implements RoomServiceInterface {

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public Room createRoom(Room room) {
		
		return roomRepository.save(room);
	}

	@Override
	public Room updateRoom(Room room) {
		
		return roomRepository.save(room);
	}

	@Override
	public void deleteRoom(Long roomId) {
		
		roomRepository.deleteById(roomId);
	}

	@Override
	public Room findRoomById(Long roomId) {
		return roomRepository.findRoomById(roomId);
	}

	@Override
	public List<Room> findAllRooms() {
		
		return roomRepository.findAll();
	}

	@Override
	public List<Room> findAvailableRooms(Long hotelId, LocalDate dateCheckIn, LocalDate checkOut,
			Integer guestsNumber) {

		return roomRepository.findAvailableRoomsByHotelAndDatesAndCapacity(hotelId, dateCheckIn, checkOut, guestsNumber);
	}

	@Override
	public Room findRoomByHotelIdAndRoomNumber(Long hotelId, Integer roomNumber) {
		
		return roomRepository.findRoomByHotelIdAndRoomNumber(hotelId, roomNumber);
	}
}
