package com.hotel.manager.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.entities.Room;
import com.hotel.manager.repositories.HotelRepository;
import com.hotel.manager.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private HotelRepository hotelRepository;

	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	public Room findById(Long id) {
		Optional<Room> obj = roomRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Room not found"));
	}

	public Room save(Room room) {
		return roomRepository.save(room);
	}

	public void deleteById(Long id) {
		roomRepository.deleteById(id);
	}

	public Room createRoom(String hotelName, Integer capacity, Integer singleBeds, Integer coupleBeds, Double diaryValue, Integer roomNumber) {
		Hotel hotel = hotelRepository.findByHotelName(hotelName)
				.orElseThrow(() -> new RuntimeException("Hotel " + hotelName + " not found"));
		List<Room> rooms = hotel.getRooms();
		for (Room room : rooms) {
			if (roomNumber == room.getRoomNumber()) {
				throw new RuntimeException("Room "+ roomNumber +" already exists");
			}
		}
		Room room = new Room(null, capacity, singleBeds, coupleBeds, diaryValue, hotel, roomNumber);
		return room;
	}

	@Transactional
	public Room updateRoom(Long roomId, Integer capacity, Integer singleBeds, Integer coupleBeds, Double diaryValue,
			String hotelName, Integer roomNumber) {
		Room room = roomRepository.findById(roomId)
				.orElseThrow(() -> new RuntimeException("Hotel " + hotelName + " room " + roomNumber + "not found!"));
		room.setCapacity(capacity);
		room.setSingleBeds(singleBeds);
		room.setCoupleBeds(coupleBeds);
		room.setDiaryValue(diaryValue);
		room.setHotel(room.getHotel());

		return this.save(room);
	}
	
	public List<Room> findAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut, Integer guestsNumber) {
        return roomRepository.findAvailableRoomsByHotelAndDatesAndCapacity(hotelId, checkIn, checkOut, guestsNumber);
    }
}
