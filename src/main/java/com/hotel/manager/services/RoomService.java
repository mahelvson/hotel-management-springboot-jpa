package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Room;
import com.hotel.manager.repositories.RoomRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;

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
}
