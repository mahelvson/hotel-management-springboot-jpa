package com.hotel.manager.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hotel.manager.dto.BookingDTO;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Room;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.exceptions.InvalidDataException;
import com.hotel.manager.facade.BookingFacade;
import com.hotel.manager.interfaces.BookingServiceInterface;
import com.hotel.manager.interfaces.ClientServiceInterface;
import com.hotel.manager.interfaces.RoomServiceInterface;
import com.hotel.manager.services.BookingService;
import com.hotel.manager.services.ClientService;
import com.hotel.manager.services.RoomService;

public class BookingFacadeUnitTest {

    private BookingFacade bookingFacade;
    private BookingServiceInterface bookingService;
    private RoomServiceInterface roomService;
    private ClientServiceInterface clientService;

    private Client client;
    private Room room;
    private BookingDTO bookingDTO;

    @BeforeEach
    public void setup() {

        bookingService = new BookingService();
        roomService = new RoomService();
        clientService = new ClientService();
        bookingFacade = new BookingFacade();
        bookingFacade.setBookingService(bookingService);
        bookingFacade.setRoomService(roomService);
        bookingFacade.setUserService(clientService);

        client = new Client();
        client.setId(1L);
        client.setName("João Paulo");
        client.setEmail("jp@jp.com");
        client.setPassword("pass123");
        client.setUserType(UserType.CLIENT);

        room = new Room();
        room.setId(1L);
        room.setRoomNumber(101);
        room.setCapacity(2);
        room.setDiaryValue(200.0);

        bookingDTO = new BookingDTO();
        bookingDTO.setClientId(1L);
        bookingDTO.setRoomId(1L);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(5));
        bookingDTO.setGuestsNumber(2);
    }

    
    @Test
    public void testCreateBookingForSelfInvalidData() {

        bookingDTO.setClientId(null);

        assertThrows(InvalidDataException.class, () -> {
            bookingFacade.createBookingForSelf(bookingDTO);
        });
        bookingDTO.setCheckIn(LocalDate.now().plusDays(5));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(1));

        assertThrows(InvalidDataException.class, () -> {
            bookingFacade.createBookingForSelf(bookingDTO);
        });
    }
    
}
