package com.hotel.manager.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	public User () {
		
	}

	public User(String name, String email, String password, UserType userType) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}
	
	public Booking createBooking(Room room, LocalDate checkIn, LocalDate checkOut, int guestsNumber) {
        double total = calculateTotal(checkIn, checkOut, room.getDiaryValue());
        return new Booking(null, checkIn, checkOut, BookingStatus.WAITING_PAYMENT, total, room, guestsNumber);
    }
	
	public Booking createBooking(Client client, Room room, LocalDate checkIn, LocalDate checkOut, int guestsNumber) {
		double total = calculateTotal(checkIn, checkOut, room.getDiaryValue());
        return new Booking(client, checkIn, checkOut, BookingStatus.WAITING_PAYMENT, total, room, guestsNumber);
		
	}

    protected double calculateTotal(LocalDate checkIn, LocalDate checkOut, double diaryValue) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        return days * diaryValue;
    }	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, userType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && userType == other.userType;
	}

}
