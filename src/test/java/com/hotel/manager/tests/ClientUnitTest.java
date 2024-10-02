package com.hotel.manager.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Room;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.UserType;

class ClientUnitTest {
	
	private Client client;
	private Booking booking;
	private Room room;
	private Booking bk;
	
	@BeforeEach
	public void setup() {
		client = new Client();
        client.setId(1L);
        client.setName("João Paulo");
        client.setEmail("jp@jp.com");
        client.setPassword("pass123");
        client.setUserType(UserType.CLIENT);
        client.setIsLoyaltyMember(false);
        
        room = new Room();
        room.setId(1L);
        room.setRoomNumber(101);
        room.setCapacity(4);
        room.setDiaryValue(200.0);
        
        LocalDate checkIn = LocalDate.of(2024, 8, 10);
        LocalDate checkOut = LocalDate.of(2024, 8, 15);
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = days * room.getDiaryValue();
        booking = new Booking(client, checkIn, checkOut, BookingStatus.WAITING_PAYMENT, total, room, 4);
        bk = client.createBooking(room, LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 15), 4);
	}

	
	
	@Test
	void testCreateBookingTotalSuccess() {
		assertEquals(bk, booking);
	}

}
