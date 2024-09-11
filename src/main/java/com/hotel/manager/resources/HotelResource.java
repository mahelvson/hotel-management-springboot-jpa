package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.services.HotelService;

@RestController
@RequestMapping(value="/hotels")
public class HotelResource {
	
	@Autowired
	private HotelService hotelService;
	
	@GetMapping
	public ResponseEntity<List<Hotel>> findAll() {
		List<Hotel> hotel = hotelService.findAll();		
		return ResponseEntity.ok().body(hotel);
	}
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<Hotel> findById(@PathVariable Long id) {
		Hotel hotel = hotelService.findById(id);
        return ResponseEntity.ok().body(hotel);
    }
	@GetMapping(value = "/{name}")
    public ResponseEntity<Hotel> findByName(@PathVariable String name) {
		Hotel hotel = hotelService.findByName(name);
        return ResponseEntity.ok().body(hotel);
    }
	@GetMapping(value = "/{city}")
    public ResponseEntity<Hotel> findByCity(@PathVariable String city) {
		Hotel hotel = hotelService.findByCity(city);
        return ResponseEntity.ok().body(hotel);
    }	
}
