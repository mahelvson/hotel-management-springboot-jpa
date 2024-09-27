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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value="/hotels")
public class HotelResource {
	
	@Autowired
	private HotelFacade hotelFacade;
	
	@GetMapping
	@Tag(name="Hotel")
	@Operation(
			summary = "Search for all hotels registered",
			description = "Return the list of all hotels",
			tags = {"receptionist"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All hotels listed"),
					@ApiResponse(responseCode = "404", description = "Hotels not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Hotel>> findAll() {
		List<Hotel> hotel = hotelFacade.getAllHotels();		
		return ResponseEntity.ok().body(hotel);
	}
	
	@GetMapping(value = "/id/{id}")
	@Tag(name="Hotel")
	@Operation(
			summary = "Search a hotel by ID",
			description = "Return a single hotel information passing its ID",
			tags = {"receptionist"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Hotel info listed"),
					@ApiResponse(responseCode = "404", description = "Hotel not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Hotel> findById(@PathVariable Long id) {
		Hotel hotel = hotelFacade.getHotelById(id);
        return ResponseEntity.ok().body(hotel);
    }
	@GetMapping(value = "/name/{name}")
	@Tag(name="Hotel")
	@Operation(
			summary = "Search a hotel by name",
			description = "Return a single hotel information passing its name",
			tags = {"hotel"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Hotel info listed"),
					@ApiResponse(responseCode = "404", description = "Hotel not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Hotel> findByName(@PathVariable String name) {
		Hotel hotel = hotelFacade.getHotelByName(name);
        return ResponseEntity.ok().body(hotel);
    }
	@GetMapping(value = "/city/{city}")
	@Tag(name="Hotel")
	@Operation(
			summary = "Search a hotel by city name",
			description = "Return a single hotel information passing its localization",
			tags = {"hotel"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Hotel info listed"),
					@ApiResponse(responseCode = "404", description = "Hotel not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Hotel> findByCity(@PathVariable String city) {
		Hotel hotel = hotelFacade.getHotelByCity(city);
        return ResponseEntity.ok().body(hotel);
    }
	@PostMapping
	@Tag(name="Hotel")
	@Operation(
			summary = "Register a new hotel",
			description = "Attempt to register a new hotel using request param",
			tags = {"hotel, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Hotel registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Hotel> createHotel(@RequestParam String hotelName, @RequestParam String city, @RequestParam Integer stars) {
		Hotel hotel = hotelFacade.createHotel(hotelName, city, stars);
		return ResponseEntity.ok(hotel);
	}
	@PatchMapping(value="/update")
	@Tag(name="Hotel")
	@Operation(
			summary = "Update a hotel registration",
			description = "Attempt to update the information of a hotel.",
			tags = {"hotel, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Hotel info updated"),
					@ApiResponse(responseCode = "404", description = "Hotel not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Hotel> updateHotel(@RequestParam String hotelName, @RequestParam String city, @RequestParam Integer stars) {
		Hotel updatedHotel = hotelFacade.updateHotel(hotelName, city, stars);
		
		return ResponseEntity.ok(updatedHotel);
	}
	@DeleteMapping(value = "/delete")
	@Tag(name="Hotel")
	@Operation(
			summary = "Remove a single hotel by its ID",
			description = "Delete a Hotel from database",
			tags = {"hotel, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Hotel removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Void> deleteById(@RequestParam Long id) {
		hotelFacade.deleteHotel(id);
		return ResponseEntity.noContent().build();
	}
}
