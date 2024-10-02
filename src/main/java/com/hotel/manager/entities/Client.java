package com.hotel.manager.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_client")
public class Client extends User {
	//Classe que representa um objeto do tipo Cliente, que pode criar reservas por si só.
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="client", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Booking> bookings = new ArrayList<>();
	
	private boolean isLoyaltyMember;
	public Client() {
		
	}

	public Client(String name, String email, String password, UserType userType, boolean isLoyaltyMember) {
		super(name, email, password, userType);
		this.isLoyaltyMember = isLoyaltyMember;

	}

	public List<Booking> getBookings() {
		return bookings;
	}
	
	@Override
    public Booking createBooking(Room room, LocalDate checkIn, LocalDate checkOut, int guestsNumber) {
        double total = this.calculateTotal(checkIn, checkOut, room.getDiaryValue());
        return new Booking(this, checkIn, checkOut, BookingStatus.WAITING_PAYMENT, total, room, guestsNumber);
    }

    @Override
    protected double calculateTotal(LocalDate checkIn, LocalDate checkOut, double diaryValue) {
        double baseTotal = super.calculateTotal(checkIn, checkOut, diaryValue);
        
        if (getIsLoyaltyMember()==true) {
        	
        	return baseTotal * 0.9;
        } else {
        	
        	return baseTotal;
        }
    }

    public boolean getIsLoyaltyMember() {

        return isLoyaltyMember;
    }
    
    public void setIsLoyaltyMember(boolean isLoyaltyMember) {
    	this.isLoyaltyMember = isLoyaltyMember;
  
    }
}
