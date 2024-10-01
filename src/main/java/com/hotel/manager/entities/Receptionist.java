package com.hotel.manager.entities;

import java.time.LocalDate;

import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_receptionist")
public class Receptionist extends User {

	private static final long serialVersionUID = 1L;
	
	public boolean canGrantDiscount;
	
	public Receptionist() {
		
	}

	public Receptionist(String name, String email, String password, UserType userType, boolean canGrantDiscount) {
		super(name, email, password, userType);
		this.canGrantDiscount = canGrantDiscount;
	}
	
	@Override
    public Booking createBooking(Client client, Room room, LocalDate checkIn, LocalDate checkOut, int guestsNumber) {
        double total = super.calculateTotal(checkIn, checkOut, room.getDiaryValue());
        
        
        if (this.isCanGrantDiscount()) {
            total *= 0.95;
        }

        
        return new Booking(client, checkIn, checkOut, BookingStatus.CONFIRMED, total, room, guestsNumber);
    }

	public boolean isCanGrantDiscount() {
		return canGrantDiscount;
	}

	public void setCanGrantDiscount(boolean canGrantDiscount) {
		this.canGrantDiscount = canGrantDiscount;
	}
	
	
}
