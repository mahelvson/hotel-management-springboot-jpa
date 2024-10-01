package com.hotel.manager.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.dto.RoomDTO;
import com.hotel.manager.entities.Hotel;
import com.hotel.manager.entities.Room;
import com.hotel.manager.exceptions.RoomAlreadyExistsException;
import com.hotel.manager.interfaces.HotelServiceInterface;
import com.hotel.manager.interfaces.RoomServiceInterface;

@Component
public class RoomFacade {
	
	@Autowired
	private RoomServiceInterface roomService;
	
	@Autowired
	private HotelServiceInterface hotelService;
	
	
	public List<Room> findAllRooms() {
		return roomService.findAllRooms();
	}
	
	public Room findRoomById(Long roomId) {
		Room room = roomService.findRoomById(roomId); 
		return room;
	}
	
	public Room createRoom(RoomDTO roomData) {
		Room room = roomService.findRoomByHotelIdAndRoomNumber(roomData.getHotelId(), roomData.getRoomNumber());
		
		if (room == null) {
			Hotel hotel = hotelService.findById(roomData.getHotelId());
			return roomService.createRoom(new Room(roomData.getSingleBeds(), roomData.getCoupleBeds(), roomData.getDiaryValue(), hotel, roomData.getRoomNumber()));	
		} else {
			throw new RoomAlreadyExistsException("This room already exists.");
		}
	}
	
	public Room updateRoom(Long roomId, RoomDTO roomData) {
		Room existingRoom = roomService.findRoomById(roomId);
		
		existingRoom.setCapacity(roomData.getCapacity() == null ? existingRoom.getCapacity() : roomData.getCapacity());
		existingRoom.setSingleBeds(roomData.getSingleBeds() == null ? existingRoom.getSingleBeds() : roomData.getSingleBeds());
		existingRoom.setCoupleBeds(roomData.getCoupleBeds() == null ? existingRoom.getCoupleBeds() : roomData.getCoupleBeds());
		existingRoom.setDiaryValue(roomData.getDiaryValue() == null ? existingRoom.getDiaryValue() : roomData.getDiaryValue());
		existingRoom.setRoomNumber(roomData.getRoomNumber() == null ? existingRoom.getRoomNumber() : roomData.getRoomNumber());
		
		return roomService.updateRoom(existingRoom);
	}
	public void deleteRoom(Long id) {
		roomService.deleteRoom(id);
	}
	
	public List<Room> findAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut, Integer guestsNumber) {
		List<Room> rooms = roomService.findAvailableRooms(hotelId, checkIn, checkOut, guestsNumber);
		return rooms;
	}
}
