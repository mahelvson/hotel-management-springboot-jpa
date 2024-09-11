package com.hotel.manager.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hotel.manager.enums.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_booking")
public class Booking implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dateCheckIn;
	private LocalDate dateCheckOut;
	
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	private Double total;
	
	@ManyToMany
	@JoinTable(
			name="tb_booking_room",
			joinColumns = @JoinColumn(name="booking_id"),
			inverseJoinColumns = @JoinColumn(name="room_id")
	)
	private List<Room> rooms = new ArrayList<>();
	
	@OneToOne(mappedBy="booking", cascade = CascadeType.ALL)
	private Payment payment;
	
	public Booking() {
		
	}
	
	public Booking(Long id, LocalDate dateCheckIn, LocalDate dateCheckOut, Client client, BookingStatus bookingStatus,
			Double total, List<Room> rooms) {
		
		this.id = id;
		this.dateCheckIn = dateCheckIn;
		this.dateCheckOut = dateCheckOut;
		this.client = client;
		this.bookingStatus = bookingStatus;
		this.total = total;
		this.rooms = rooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateCheckIn() {
		return dateCheckIn;
	}

	public void setDateCheckIn(LocalDate dateCheckIn) {
		this.dateCheckIn = dateCheckIn;
	}

	public LocalDate getDateCheckOut() {
		return dateCheckOut;
	}

	public void setDateCheckOut(LocalDate dateCheckOut) {
		this.dateCheckOut = dateCheckOut;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
}
