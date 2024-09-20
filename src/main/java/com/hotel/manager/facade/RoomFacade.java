package com.hotel.manager.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Room;
import com.hotel.manager.services.RoomService;

@Component
public class RoomFacade {
	
	@Autowired
	private RoomService roomService;
	
	
	public List<Room> getAllRooms() {
		List<Room> rooms = roomService.findAll();
		return rooms;
	}
	
	public Room getRoomById(Long roomId) {
		Room room = roomService.findById(roomId);
		return room;
	}
	
	public Room createRoom(String hotelName, Integer capacity, Integer singleBeds, Integer coupleBeds, Double diaryValue, Integer roomNumber) {
		Room room = roomService.createRoom(hotelName, capacity, singleBeds, coupleBeds, diaryValue, roomNumber);
		return room;
	}
	public void deleteRoom(Long roomId) {
		roomService.deleteById(roomId);
	}
	
	public List<Room> findAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut, Integer guestsNumber) {
		List<Room> rooms = roomService.findAvailableRooms(hotelId, checkIn, checkOut, guestsNumber);
		return rooms;
	}
}
