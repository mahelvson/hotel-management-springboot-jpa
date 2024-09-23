package com.hotel.manager.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_room")
public class Room implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Integer roomNumber;
	private Integer capacity;
	private Integer singleBeds;
	private Integer coupleBeds;
	private Double diaryValue;
	@ElementCollection
	private List<LocalDate> bookedIn = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="hotel_id")
	private Hotel hotel;
	
	@OneToMany(mappedBy="room")
	@JsonIgnore
	private List<Booking> bookings = new ArrayList<>();
	
	public Room() {
		
	}
	
	public Room(Long id, Integer singleBeds, Integer coupleBeds, Double diaryValue,
			Hotel hotel, Integer roomNumber) {
		
		this.id = id;
		this.capacity = singleBeds * 1 + coupleBeds * 2;
		this.singleBeds = singleBeds;
		this.coupleBeds = coupleBeds;
		this.diaryValue = diaryValue;
		this.hotel = hotel;
		this.roomNumber = roomNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getSingleBeds() {
		return singleBeds;
	}

	public void setSingleBeds(Integer singleBeds) {
		this.singleBeds = singleBeds;
	}

	public Integer getCoupleBeds() {
		return coupleBeds;
	}

	public void setCoupleBeds(Integer coupleBeds) {
		this.coupleBeds = coupleBeds;
	}

	public Double getDiaryValue() {
		return diaryValue;
	}

	public void setDiaryValue(Double diaryValue) {
		this.diaryValue = diaryValue;
	}

	public List<LocalDate> getBookedIn() {
		return bookedIn;
	}

	public void setBookedIn(List<LocalDate> dates) {
		this.bookedIn = dates;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}	

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
