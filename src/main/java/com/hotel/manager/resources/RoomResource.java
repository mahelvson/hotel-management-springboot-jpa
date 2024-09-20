package com.hotel.manager.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Room;
import com.hotel.manager.facade.RoomFacade;
import com.hotel.manager.repositories.RoomRepository;

@RestController
@RequestMapping(value="/rooms")
public class RoomResource {
	
	@Autowired
	private RoomFacade roomFacade;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@GetMapping
	public ResponseEntity<List<Room>> findAll() {
		List<Room> room = roomFacade.getAllRooms();
		return ResponseEntity.ok().body(room);
	}
	
	@GetMapping(value = "/id/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long roomId) {
		Room room = roomFacade.getRoomById(roomId);
        return ResponseEntity.ok().body(room);
    }
	
	@PostMapping
	public ResponseEntity<Room> createRoom(@RequestParam Integer capacity, @RequestParam Integer singleBeds, @RequestParam Integer coupleBeds, @RequestParam Double diaryValue, @RequestParam String hotelName, @RequestParam Integer roomNumber) {
		Room room = roomFacade.createRoom(hotelName, capacity, singleBeds, coupleBeds, diaryValue, roomNumber);
		roomRepository.save(room);
		return ResponseEntity.ok(room);
	}
	
	@DeleteMapping(value="/deteByNameNumber")
	public ResponseEntity<Void> deleteById(@RequestParam Long roomId) {
		roomFacade.deleteRoom(roomId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam Long hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam Integer guestsNumber) {

        List<Room> availableRooms = roomFacade.findAvailableRooms(hotelId, checkIn, checkOut, guestsNumber);
        return ResponseEntity.ok().body(availableRooms);
    }
}
