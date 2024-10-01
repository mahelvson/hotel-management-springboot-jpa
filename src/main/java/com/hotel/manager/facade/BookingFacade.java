package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.dto.BookingDTO;
import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Payment;
import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.entities.Room;
import com.hotel.manager.entities.User;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.PaymentStatus;
import com.hotel.manager.exceptions.ConfirmedBookingException;
import com.hotel.manager.exceptions.RoomNotFoundException;
import com.hotel.manager.exceptions.RoomUnavailableException;
import com.hotel.manager.interfaces.BookingServiceInterface;
import com.hotel.manager.interfaces.RoomServiceInterface;
import com.hotel.manager.interfaces.UserServiceInterface;

@Component
public class BookingFacade {
	
	@Autowired
	private BookingServiceInterface bookingService;
	
	@Autowired
	private RoomServiceInterface roomService;
	
	@Autowired UserServiceInterface userService;
	
	
	public Booking createBookingForSelf(BookingDTO bookingData) {
		
		User user = userService.findById(bookingData.getClientId());
	    if (!(user instanceof Client)) {
	        throw new IllegalArgumentException("User with ID " + bookingData.getClientId() + " is not a Client.");
	    }
	    Client client = (Client) user;
		Room room = roomService.findRoomById(bookingData.getRoomId());
		
		if (room == null) {
			throw new RoomNotFoundException("This room does not exist");
		}
		
		List<Booking> conflictingBookings = bookingService.findConflictBookings(bookingData.getRoomId(), bookingData.getCheckIn(), bookingData.getCheckOut());
		
		if (bookingData.getCheckIn().isAfter(bookingData.getCheckOut())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }
		
		if (!conflictingBookings.isEmpty()) {
			throw new RoomUnavailableException("Room is already booked for the selected dates");
		}
		Booking booking = client.createBooking(room, bookingData.getCheckIn(), bookingData.getCheckOut(), bookingData.getGuestsNumber());
		
		Payment payment = new Payment();
        payment.setValue(booking.getTotal());
        payment.setPaid(PaymentStatus.PENDING);
        booking.setPayment(payment);
       
		return bookingService.createBooking(booking);
		
	}
	
	public Booking updateBookingForSelf(Long bookingId, BookingDTO newBookingData) {
		Booking booking = bookingService.findBookingById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
	    Client client = booking.getClient();
		
		if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
			throw new ConfirmedBookingException("Booking already confirmed. It is not possible to update.");
		}
		Room room = booking.getRoom();
		Room newRoom = roomService.findRoomById(newBookingData.getRoomId());
		
		if (newRoom != null) {
			room = newRoom;
		}
		List<Booking> conflictingBookings = bookingService.findConflictBookings(room.getId(), newBookingData.getCheckIn() == null ? booking.getDateCheckIn() : newBookingData.getCheckIn(), newBookingData.getCheckOut() == null ? booking.getDateCheckOut() : newBookingData.getCheckOut());
		if (conflictingBookings.size() == 1) {
			if (conflictingBookings.get(0).getId() != booking.getId()) {
				throw new RuntimeException("New room is already booked for the selected dates");				
			}
		}
		
		if (newBookingData.getRoomId() != null) {
			room = roomService.findRoomById(newBookingData.getRoomId());
		}
			
		
		Booking newBooking = client.createBooking(room, 
				newBookingData.getCheckIn() == null ? booking.getDateCheckIn() : newBookingData.getCheckIn(),
				newBookingData.getCheckOut() == null ? booking.getDateCheckOut() : newBookingData.getCheckOut(), -1);
		
		booking.setRoom(room);
		booking.setDateCheckIn(newBooking.getDateCheckIn());
		booking.setDateCheckOut(newBooking.getDateCheckOut());
		
		if (newBookingData.getGuestsNumber() <= room.getCapacity() && newBookingData.getGuestsNumber() != null) {
			booking.setGuestsNumber(newBookingData.getGuestsNumber());				
		} else {
			throw new RoomUnavailableException("This room has not enough capacity.");			
		}
		booking.setTotal(newBooking.getTotal()); 
		
		return bookingService.updateBooking(booking);
	}
	
	public Booking createBookingForClient(Long receptionistId, BookingDTO bookingData) {
		
		User userRecept = userService.findById(receptionistId);
	    if (!(userRecept instanceof Receptionist)) {
	        throw new IllegalArgumentException("User with ID " + receptionistId + " is not a receptionist.");
	    }
	    Receptionist receptionist = (Receptionist) userRecept;
	    
	    User user = userService.findById(bookingData.getClientId());
	    if (!(user instanceof Client)) {
	        throw new IllegalArgumentException("User with ID " + bookingData.getClientId() + " is not a Client.");
	    }
	    Client client = (Client) user;
		Room room = roomService.findRoomById(bookingData.getRoomId());
		
		if (room == null) {
			throw new RoomNotFoundException("This room does not exist");
		}
		
		List<Booking> conflictingBookings = bookingService.findConflictBookings(bookingData.getRoomId(), bookingData.getCheckIn(), bookingData.getCheckOut());
		
		if (bookingData.getCheckIn().isAfter(bookingData.getCheckOut())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }
		
		if (!conflictingBookings.isEmpty()) {
			throw new RoomUnavailableException("Room is already booked for the selected dates");
		}
		
		Booking booking = receptionist.createBooking(client, room, bookingData.getCheckIn(), bookingData.getCheckOut(), bookingData.getGuestsNumber());
		Payment payment = new Payment();
		payment.setValue(booking.getTotal());
		payment.setPaid(PaymentStatus.PAID);
		booking.setPayment(payment);
		
		return bookingService.createBooking(booking);	
	}
	
	public Booking confirmBooking(Long bookingId) {
		
		Booking booking = bookingService.findBookingById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
		booking.setBookingStatus(BookingStatus.CONFIRMED);
		return bookingService.updateBooking(booking);	
	}
	
	public Booking findBookingById(Long bookingId) {
		Booking booking = bookingService.findBookingById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
		return booking;
	}
	
	public void deleteBooking(Long bookingId) {
		bookingService.deleteBooking(bookingId);
	}
	
	public List<Booking> findBookingsByClientId(Long clientId) {
		return bookingService.findBookingsByClientId(clientId);
	}
	
	public List<Booking> findAllBookings() {
		return bookingService.findAllBookings();
	}

	public void ConfirmPayment(Booking booking) {
		bookingService.ConfirmPayment(booking);
	}
	

}
