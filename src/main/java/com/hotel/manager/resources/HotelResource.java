package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Hotel;
import com.hotel.manager.facade.HotelFacade;

@RestController
@RequestMapping(value="/hotels")
public class HotelResource {
	
	@Autowired
	private HotelFacade hotelFacade;
	
	@GetMapping
	public ResponseEntity<List<Hotel>> findAll() {
		List<Hotel> hotel = hotelFacade.getAllHotels();		
		return ResponseEntity.ok().body(hotel);
	}
	
	@GetMapping(value = "/id/{id}")
    public ResponseEntity<Hotel> findById(@PathVariable Long id) {
		Hotel hotel = hotelFacade.getHotelById(id);
        return ResponseEntity.ok().body(hotel);
    }
	@GetMapping(value = "/name/{name}")
    public ResponseEntity<Hotel> findByName(@PathVariable String name) {
		Hotel hotel = hotelFacade.getHotelByName(name);
        return ResponseEntity.ok().body(hotel);
    }
	@GetMapping(value = "/city/{city}")
    public ResponseEntity<Hotel> findByCity(@PathVariable String city) {
		Hotel hotel = hotelFacade.getHotelByCity(city);
        return ResponseEntity.ok().body(hotel);
    }
	@PatchMapping(value="/update/{name}")
	public ResponseEntity<Hotel> updateHotel(@RequestParam String hotelName, @RequestParam String city, @RequestParam Integer stars) {
		Hotel updatedHotel = hotelFacade.updateHotel(hotelName, city, stars);
		
		return ResponseEntity.ok(updatedHotel);
	}
	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestParam String hotelName, @RequestParam String city, @RequestParam Integer stars) {
		Hotel hotel = hotelFacade.createHotel(hotelName, city, stars);
		return ResponseEntity.ok(hotel);
	}
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteById(@RequestParam Long id) {
		hotelFacade.deleteHotel(id);
		return ResponseEntity.noContent().build();
	}
}
