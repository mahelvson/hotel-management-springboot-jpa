package com.hotel.manager.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.manager.dto.BookingDTO;
import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Room;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.exceptions.BookingNotFoundException;
import com.hotel.manager.exceptions.InvalidDataException;
import com.hotel.manager.facade.BookingFacade;
import com.hotel.manager.services.ClientService;
import com.hotel.manager.services.RoomService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookingFacadeIntegrationTest {

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoomService roomService;

    private Client client;
    private Room room;
    private BookingDTO bookingDTO;

    @BeforeEach
    public void setup() {
        client = new Client();
        client.setName("João Paulo");
        client.setEmail("jp@jp.com");
        client.setPassword("pass123");
        client.setUserType(UserType.CLIENT);
        client = clientService.createClient(client);

        room = new Room();
        room.setRoomNumber(101);
        room.setCapacity(2);
        room.setDiaryValue(100.0);
        room = roomService.createRoom(room);

        bookingDTO = new BookingDTO();
        bookingDTO.setClientId(client.getId());
        bookingDTO.setRoomId(room.getId());
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(5));
        bookingDTO.setGuestsNumber(2);
    }

    @Test
    @Transactional
    public void testCreateBookingForSelf_Success() {
        Booking booking = bookingFacade.createBookingForSelf(bookingDTO);

        assertNotNull(booking);
        assertEquals(client.getId(), booking.getClient().getId());
        assertEquals(room.getId(), booking.getRoom().getId());
        assertTrue(booking.getTotal() > 0);
    }

    @Test
    @Transactional
    public void testFindBookingById_Success() {
        Booking createdBooking = bookingFacade.createBookingForSelf(bookingDTO);

        Booking foundBooking = bookingFacade.findBookingById(createdBooking.getId());
        assertNotNull(foundBooking);
        assertEquals(createdBooking.getId(), foundBooking.getId());
    }

    @Test
    @Transactional
    public void testFindBookingById_NotFound() {
        assertThrows(BookingNotFoundException.class, () -> {
            bookingFacade.findBookingById(999L);
        });
    }

    @Test
    @Transactional
    public void testCreateBookingForSelf_InvalidData() {
        bookingDTO.setClientId(null);

        assertThrows(InvalidDataException.class, () -> {
            bookingFacade.createBookingForSelf(bookingDTO);
        });
    }
}
