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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value="/rooms")
public class RoomResource {
	
	@Autowired
	private RoomFacade roomFacade;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@GetMapping
	@Tag(name="Room")
	@Operation(
			summary = "Search for all rooms",
			description = "Return the list of all rooms",
			tags = {"room"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All rooms listed"),
					@ApiResponse(responseCode = "404", description = "Room not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Room>> findAll() {
		List<Room> room = roomFacade.getAllRooms();
		return ResponseEntity.ok().body(room);
	}
	
	@GetMapping(value = "/id/{id}")
	@Tag(name="Room")
	@Operation(
			summary = "Search a room by ID",
			description = "Return a single room information passing its ID",
			tags = {"room"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Room info listed"),
					@ApiResponse(responseCode = "404", description = "Room not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Room> findById(@PathVariable Long roomId) {
		Room room = roomFacade.getRoomById(roomId);
        return ResponseEntity.ok().body(room);
    }
	
	@PostMapping
	@Tag(name="Room")
	@Operation(
			summary = "Register a new room",
			description = "Attempt to register a new room using request param (not request body as in client)",
			tags = {"room, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Room registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Room> createRoom(@RequestParam Integer capacity, @RequestParam Integer singleBeds, @RequestParam Integer coupleBeds, @RequestParam Double diaryValue, @RequestParam String hotelName, @RequestParam Integer roomNumber) {
		Room room = roomFacade.createRoom(hotelName, capacity, singleBeds, coupleBeds, diaryValue, roomNumber);
		roomRepository.save(room);
		return ResponseEntity.ok(room);
	}
	
	@DeleteMapping(value="/deleteById")
	@Tag(name="Room")
	@Operation(
			summary = "Remove a single room by its ID",
			description = "Delete a room from database",
			tags = {"room, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Room removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Void> deleteById(@RequestParam Long roomId) {
		roomFacade.deleteRoom(roomId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/available")
	@Tag(name="Room")
	@Operation(
			summary = "List all available rooms in a hotel",
			description = "List available rooms considering the check in and check out date and the gusts number",
			tags = {"room, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Room removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam Long hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam Integer guestsNumber) {

        List<Room> availableRooms = roomFacade.findAvailableRooms(hotelId, checkIn, checkOut, guestsNumber);
        return ResponseEntity.ok().body(availableRooms);
    }
}
