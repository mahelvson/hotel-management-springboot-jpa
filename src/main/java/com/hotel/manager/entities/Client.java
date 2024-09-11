package com.hotel.manager.entities;

import java.util.ArrayList;
import java.util.List;

import com.hotel.manager.enums.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_client")
public class Client extends User{
	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="client", cascade=CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<>();
	
	public Client() {
		
	}

	public Client(Long id, String nome, String email, String password, UserType userType) {
		super(id, nome, email, password, userType);

	}

	public List<Booking> getBookings() {
		return bookings;
	}	
}
