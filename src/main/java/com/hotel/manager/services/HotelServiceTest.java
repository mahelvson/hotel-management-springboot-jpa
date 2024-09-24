package com.hotel.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.hotel.manager.model.Hotel;
import com.hotel.manager.repository.HotelRepository;
import com.hotel.manager.exceptions.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    public HotelServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHotelById() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel(hotelId, "Hotel Name", "Address");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(hotelId);
        assertEquals("Hotel Name", result.getName());
    }

    @Test
    public void testGetHotelById_NotFound() {
        Long hotelId = 1L;

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        try {
            hotelService.getHotelById(hotelId);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Hotel not found", ex.getMessage());
        }
    }
}
