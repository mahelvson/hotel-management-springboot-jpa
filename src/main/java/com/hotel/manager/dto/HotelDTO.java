package com.hotel.manager.dto;

public class HotelDTO {
	
	private String hotelName;
	private String city;
	private Integer stars;
	
	public HotelDTO() {
		
	}

	public HotelDTO(String hotelName, String city, Integer stars) {
		this.hotelName = hotelName;
		this.city = city;
		this.stars = stars;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
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
}
