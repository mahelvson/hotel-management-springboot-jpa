package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Room;
import com.hotel.manager.services.RoomService;

@RestController
@RequestMapping(value="/rooms")
public class RoomResource {
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public ResponseEntity<List<Room>> findAll() {
		List<Room> room = roomService.findAll();		
		return ResponseEntity.ok().body(room);
	}
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long id) {
		Room room = roomService.findById(id);
        return ResponseEntity.ok().body(room);
    }
}
