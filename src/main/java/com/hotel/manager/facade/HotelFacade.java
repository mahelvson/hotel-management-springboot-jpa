package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.dto.HotelDTO;
import com.hotel.manager.entities.Hotel;
import com.hotel.manager.exceptions.HotelAlreadyExistsException;
import com.hotel.manager.exceptions.HotelNotFoundException;
import com.hotel.manager.exceptions.InvalidDataException;
import com.hotel.manager.services.HotelService;

@Component
public class HotelFacade {

    @Autowired
    private HotelService hotelService;

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = hotelService.findAll();
        
        if (hotels == null || hotels.isEmpty()) {
            throw new HotelNotFoundException("No hotels found");
        }
        
        return hotels;
    }

    public void deleteHotel(Long id) {
        if (id == null) {
            throw new InvalidDataException("Hotel ID cannot be null");
        }

        Hotel hotel = hotelService.findById(id);
        if (hotel == null) {
            throw new HotelNotFoundException("Hotel with ID " + id + " not found");
        }

        hotelService.deleteHotel(id);
    }

    public Hotel findHotelById(Long id) {
        if (id == null) {
            throw new InvalidDataException("Hotel ID cannot be null");
        }

        Hotel hotel = hotelService.findById(id);
        if (hotel == null) {
            throw new HotelNotFoundException("Hotel with ID " + id + " not found");
        }

        return hotel;
    }

    public Hotel getHotelByName(String hotelName) {
        if (hotelName == null || hotelName.trim().isEmpty()) {
            throw new InvalidDataException("Hotel name cannot be null or empty");
        }

        Hotel hotel = hotelService.findByName(hotelName);
        if (hotel == null) {
            throw new HotelNotFoundException("Hotel with name " + hotelName + " not found");
        }

        return hotel;
    }

    public Hotel updateHotel(Long hotelId, HotelDTO hotelDTO) {
        if (hotelId == null) {
            throw new InvalidDataException("Hotel ID cannot be null");
        }

        if (hotelDTO == null) {
            throw new InvalidDataException("Hotel data cannot be null");
        }

        Hotel hotel = hotelService.findById(hotelId);
        if (hotel == null) {
            throw new HotelNotFoundException("Hotel with ID " + hotelId + " not found");
        }

        hotel.setName(hotelDTO.getHotelName() == null ? hotel.getName() : hotelDTO.getHotelName());
        hotel.setStars(hotelDTO.getStars() == null ? hotel.getStars() : hotelDTO.getStars());
        hotel.setCity(hotelDTO.getCity() == null ? hotel.getCity() : hotelDTO.getCity());

        return hotelService.updateHotel(hotel);
    }

    public Hotel createHotel(HotelDTO hotelDTO) {
        if (hotelDTO == null) {
            throw new InvalidDataException("Hotel data cannot be null");
        }

        if (hotelDTO.getHotelName() == null || hotelDTO.getHotelName().trim().isEmpty()) {
            throw new InvalidDataException("Hotel name cannot be null or empty");
        }

        Hotel existingHotel = hotelService.findByName(hotelDTO.getHotelName());

        if (existingHotel != null) {
            throw new HotelAlreadyExistsException("Hotel with name " + hotelDTO.getHotelName() + " already exists");
        }

        Hotel newHotel = new Hotel(hotelDTO.getHotelName(), hotelDTO.getCity(), hotelDTO.getStars());
        return hotelService.createHotel(newHotel);
    }

    public Hotel getHotelByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new InvalidDataException("City name cannot be null or empty");
        }

        Hotel hotel = hotelService.findHotelByCity(city);
        if (hotel == null) {
            throw new HotelNotFoundException("No hotels found in the city " + city);
        }

        return hotel;
    }
}
