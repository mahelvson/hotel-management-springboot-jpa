package com.hotel.manager.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_hotel")
public class Hotel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String hotelName;
	private String city;
	private Integer stars;
	
	@OneToMany(mappedBy="hotel", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Room> rooms;
	
	public Hotel() {
		
	}
	
	public Hotel(Long id, String hotelName, String city, Integer stars) {
		super();
		this.id = id;
		this.hotelName = hotelName;
		this.city = city;
		this.stars = stars;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return hotelName;
	}
	
	public void setName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public Integer getStars() {
		return stars;
	}
	
	public void setStars(Integer stars) {
		this.stars = stars;
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
		Hotel other = (Hotel) obj;
		return Objects.equals(id, other.id);
	}
}
