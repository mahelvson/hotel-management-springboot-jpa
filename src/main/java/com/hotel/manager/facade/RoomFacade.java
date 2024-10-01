package com.hotel.manager.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.dto.RoomDTO;
import com.hotel.manager.entities.Hotel;
import com.hotel.manager.entities.Room;
import com.hotel.manager.exceptions.RoomAlreadyExistsException;
import com.hotel.manager.exceptions.RoomNotFoundException;
import com.hotel.manager.exceptions.InvalidDataException;
import com.hotel.manager.interfaces.HotelServiceInterface;
import com.hotel.manager.interfaces.RoomServiceInterface;

@Component
public class RoomFacade {

	@Autowired
	private RoomServiceInterface roomService;

	@Autowired
	private HotelServiceInterface hotelService;

	public List<Room> findAllRooms() {
		List<Room> rooms = roomService.findAllRooms();
		if (rooms == null || rooms.isEmpty()) {
			throw new RoomNotFoundException("No rooms found");
		}
		return rooms;
	}

	public Room findRoomById(Long roomId) {
		if (roomId == null) {
			throw new InvalidDataException("Room ID cannot be null");
		}

		Room room = roomService.findRoomById(roomId);
		if (room == null) {
			throw new RoomNotFoundException("Room with ID " + roomId + " not found");
		}
		return room;
	}

	public Room createRoom(RoomDTO roomData) {
		if (roomData == null) {
			throw new InvalidDataException("Room data cannot be null");
		}

		if (roomData.getHotelId() == null) {
			throw new InvalidDataException("Hotel ID cannot be null");
		}

		if (roomData.getRoomNumber() == null) {
			throw new InvalidDataException("Room number cannot be null");
		}

		Room existingRoom = roomService.findRoomByHotelIdAndRoomNumber(roomData.getHotelId(), roomData.getRoomNumber());

		if (existingRoom != null) {
			throw new RoomAlreadyExistsException(
					"Room with number " + roomData.getRoomNumber() + " already exists in this hotel");
		}

		Hotel hotel = hotelService.findById(roomData.getHotelId());
		if (hotel == null) {
			throw new InvalidDataException("Hotel with ID " + roomData.getHotelId() + " not found");
		}

		Room newRoom = new Room(roomData.getSingleBeds(), roomData.getCoupleBeds(), roomData.getDiaryValue(), hotel,
				roomData.getRoomNumber());
		return roomService.createRoom(newRoom);
	}

	public Room updateRoom(Long roomId, RoomDTO roomData) {
		if (roomId == null) {
			throw new InvalidDataException("Room ID cannot be null");
		}

		if (roomData == null) {
			throw new InvalidDataException("Room data cannot be null");
		}

		Room existingRoom = roomService.findRoomById(roomId);
		if (existingRoom == null) {
			throw new RoomNotFoundException("Room with ID " + roomId + " not found");
		}

		existingRoom.setCapacity(roomData.getCapacity() == null ? existingRoom.getCapacity() : roomData.getCapacity());
		existingRoom.setSingleBeds(
				roomData.getSingleBeds() == null ? existingRoom.getSingleBeds() : roomData.getSingleBeds());
		existingRoom.setCoupleBeds(
				roomData.getCoupleBeds() == null ? existingRoom.getCoupleBeds() : roomData.getCoupleBeds());
		existingRoom.setDiaryValue(
				roomData.getDiaryValue() == null ? existingRoom.getDiaryValue() : roomData.getDiaryValue());
		existingRoom.setRoomNumber(
				roomData.getRoomNumber() == null ? existingRoom.getRoomNumber() : roomData.getRoomNumber());

		return roomService.updateRoom(existingRoom);
	}

	public void deleteRoom(Long id) {
		if (id == null) {
			throw new InvalidDataException("Room ID cannot be null");
		}

		Room room = roomService.findRoomById(id);
		if (room == null) {
			throw new RoomNotFoundException("Room with ID " + id + " not found");
		}

		roomService.deleteRoom(id);
	}

	public List<Room> findAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut, Integer guestsNumber) {
		if (hotelId == null) {
			throw new InvalidDataException("Hotel ID cannot be null");
		}

		if (checkIn == null || checkOut == null) {
			throw new InvalidDataException("Check-in and check-out dates cannot be null");
		}

		if (checkIn.isAfter(checkOut)) {
			throw new InvalidDataException("Check-in date cannot be after check-out date");
		}

		if (guestsNumber == null || guestsNumber <= 0) {
			throw new InvalidDataException("Guests number must be greater than 0");
		}

		List<Room> rooms = roomService.findAvailableRooms(hotelId, checkIn, checkOut, guestsNumber);
		if (rooms == null || rooms.isEmpty()) {
			throw new RoomNotFoundException("No available rooms found for the given criteria");
		}

		return rooms;
	}
}
