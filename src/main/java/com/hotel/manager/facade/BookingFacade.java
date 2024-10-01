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
import com.hotel.manager.exceptions.BookingNotFoundException;
import com.hotel.manager.exceptions.ConfirmedBookingException;
import com.hotel.manager.exceptions.InvalidDataException;
import com.hotel.manager.exceptions.RoomNotFoundException;
import com.hotel.manager.exceptions.RoomUnavailableException;
import com.hotel.manager.exceptions.UserNotFoundException;
import com.hotel.manager.interfaces.BookingServiceInterface;
import com.hotel.manager.interfaces.ClientServiceInterface;
import com.hotel.manager.interfaces.RoomServiceInterface;
import com.hotel.manager.interfaces.UserServiceInterface;

@Component
public class BookingFacade {
	
	@Autowired
	private BookingServiceInterface bookingService;
	
	@Autowired
	private RoomServiceInterface roomService;
	
	@Autowired 
	private UserServiceInterface userService;

	@Autowired
	private ClientServiceInterface clientService;
	
	
	public Booking createBookingForSelf(BookingDTO bookingData) {
		
		if (bookingData == null) {
            throw new InvalidDataException("Booking data cannot be null");
        }

        if (bookingData.getClientId() == null || bookingData.getRoomId() == null) {
            throw new InvalidDataException("Client ID and Room ID cannot be null");
        }

        User user = userService.findById(bookingData.getClientId());
        if (user == null || !(user instanceof Client)) {
            throw new UserNotFoundException("User with ID " + bookingData.getClientId() + " is not a Client or does not exist.");
        }
        Client client = (Client) user;

        Room room = roomService.findRoomById(bookingData.getRoomId());
        if (room == null) {
            throw new RoomNotFoundException("Room with ID " + bookingData.getRoomId() + " does not exist");
        }

        if (bookingData.getCheckIn() == null || bookingData.getCheckOut() == null || bookingData.getCheckIn().isAfter(bookingData.getCheckOut())) {
            throw new InvalidDataException("Invalid check-in or check-out dates");
        }

        List<Booking> conflictingBookings = bookingService.findConflictBookings(bookingData.getRoomId(), bookingData.getCheckIn(), bookingData.getCheckOut());
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
		if (bookingId == null) {
            throw new InvalidDataException("Booking ID cannot be null");
        }

        Booking booking = bookingService.findBookingById(bookingId);
        
        if (booking == null) {
        	throw new BookingNotFoundException("This booking does not exists");
        }
        
        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            throw new ConfirmedBookingException("Booking already confirmed. It is not possible to update.");
        }

        Room room = booking.getRoom();
        if (newBookingData.getRoomId() != null) {
            Room newRoom = roomService.findRoomById(newBookingData.getRoomId());
            if (newRoom == null) {
                throw new RoomNotFoundException("Room with ID " + newBookingData.getRoomId() + " does not exist");
            }
            room = newRoom;
        }

        List<Booking> conflictingBookings = bookingService.findConflictBookings(room.getId(), 
                newBookingData.getCheckIn() == null ? booking.getDateCheckIn() : newBookingData.getCheckIn(), 
                newBookingData.getCheckOut() == null ? booking.getDateCheckOut() : newBookingData.getCheckOut());
        
        if (conflictingBookings.size() > 1 || (conflictingBookings.size() == 1 && !conflictingBookings.get(0).getId().equals(booking.getId()))) {
            throw new RoomUnavailableException("The room is already booked for the selected dates");
        }

        if (newBookingData.getGuestsNumber() != null && newBookingData.getGuestsNumber() > room.getCapacity()) {
            throw new RoomUnavailableException("The room does not have enough capacity for the specified number of guests");
        }
		
		Booking newBooking = booking.getClient().createBooking(room, 
				newBookingData.getCheckIn() == null ? booking.getDateCheckIn() : newBookingData.getCheckIn(),
				newBookingData.getCheckOut() == null ? booking.getDateCheckOut() : newBookingData.getCheckOut(), -1);
		
		booking.setRoom(room);
		booking.setDateCheckIn(newBooking.getDateCheckIn());
		booking.setDateCheckOut(newBooking.getDateCheckOut());
		booking.setTotal(newBooking.getTotal()); 
		
		return bookingService.updateBooking(booking);
	}
	
	public Booking createBookingForClient(Long receptionistId, BookingDTO bookingData) {
		if (receptionistId == null || bookingData == null) {
            throw new InvalidDataException("Receptionist ID and booking data cannot be null");
        }

        User userRecept = userService.findById(receptionistId);
        if (userRecept == null || !(userRecept instanceof Receptionist)) {
            throw new UserNotFoundException("User with ID " + receptionistId + " is not a receptionist or does not exist");
        }
        Receptionist receptionist = (Receptionist) userRecept;

        User user = userService.findById(bookingData.getClientId());
        if (user == null || !(user instanceof Client)) {
            throw new UserNotFoundException("User with ID " + bookingData.getClientId() + " is not a Client or does not exist");
        }
        Client client = (Client) user;

        Room room = roomService.findRoomById(bookingData.getRoomId());
        if (room == null) {
            throw new RoomNotFoundException("Room with ID " + bookingData.getRoomId() + " does not exist");
        }

        if (bookingData.getCheckIn() == null || bookingData.getCheckOut() == null || bookingData.getCheckIn().isAfter(bookingData.getCheckOut())) {
            throw new InvalidDataException("Invalid check-in or check-out dates");
        }

        List<Booking> conflictingBookings = bookingService.findConflictBookings(bookingData.getRoomId(), bookingData.getCheckIn(), bookingData.getCheckOut());
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
		
		Booking booking = bookingService.findBookingById(bookingId);
	    if (booking == null) {
	        throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
	    }
		booking.setBookingStatus(BookingStatus.CONFIRMED);
		return bookingService.updateBooking(booking);	
	}
	
	public Booking findBookingById(Long bookingId) {
		Booking booking = bookingService.findBookingById(bookingId);
	    if (booking == null) {
	        throw new BookingNotFoundException("Booking with ID " + bookingId + " not found");
	    }
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
	
	public void setBookingService(BookingServiceInterface bookingService) {
	    this.bookingService = bookingService;
	}

	public void setRoomService(RoomServiceInterface roomService) {
	    this.roomService = roomService;
	}

	public void setUserService(ClientServiceInterface clientService) {
	    this.clientService = clientService;
	}
}
