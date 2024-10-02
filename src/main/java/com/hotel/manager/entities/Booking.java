package com.hotel.manager.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.manager.enums.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_booking")
public class Booking implements Serializable{
	// Classe que representa um objeto Booking, representado a reserva.
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dateCheckIn;
	private LocalDate dateCheckOut;
	
	@ManyToOne
	@JoinColumn(name="client_id")
	@JsonIgnore
	private Client client;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	
	@OneToOne(mappedBy="booking", cascade = CascadeType.ALL)
	private Payment payment;
	
	public Booking() {
		
	}
	
	private Integer guestsNumber;
	
	public Booking(Client client, LocalDate dateCheckIn, LocalDate dateCheckOut, BookingStatus bookingStatus,
			Double total, Room room, Integer guestsNumber) {
		this.dateCheckIn = dateCheckIn;
		this.dateCheckOut = dateCheckOut;
		this.client = client;
		this.bookingStatus = bookingStatus;
		this.total = total;
		this.room = room;
		this.guestsNumber = guestsNumber;
	}

	public Integer getGuestsNumber() {
		return this.guestsNumber;
	}
	public void setGuestsNumber(Integer guestsNumber) {
		this.guestsNumber = guestsNumber;
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

	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment != null) {
            payment.setBooking(this);
        }
    }

	@Override
	public int hashCode() {
		return Objects.hash(bookingStatus, client, dateCheckIn, dateCheckOut, guestsNumber, id, payment, room, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		return bookingStatus == other.bookingStatus && Objects.equals(client, other.client)
				&& Objects.equals(dateCheckIn, other.dateCheckIn) && Objects.equals(dateCheckOut, other.dateCheckOut)
				&& Objects.equals(guestsNumber, other.guestsNumber) && Objects.equals(id, other.id)
				&& Objects.equals(payment, other.payment) && Objects.equals(room, other.room)
				&& Objects.equals(total, other.total);
	}
	
	
}
