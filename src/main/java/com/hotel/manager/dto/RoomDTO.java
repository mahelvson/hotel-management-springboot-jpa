package com.hotel.manager.dto;

public class RoomDTO {
	
		private Long id;
		private Long hotelId;
		private Integer roomNumber;
		private Integer capacity;
		private Integer singleBeds;
		private Integer coupleBeds;
		private Double diaryValue;
		
		public RoomDTO() {
			
		}
		
		public RoomDTO(Integer singleBeds, Integer coupleBeds, Double diaryValue, Long hotelId, Integer roomNumber) {
			this.capacity = singleBeds * 1 + coupleBeds * 2;
			this.singleBeds = singleBeds;
			this.coupleBeds = coupleBeds;
			this.diaryValue = diaryValue;
			this.hotelId = hotelId;
			this.roomNumber = roomNumber;
			
		}

		public Long getId() {
			return id;
		}


		public Long getHotelId() {
			return hotelId;
		}

		public void setHotelId(Long hotelId) {
			this.hotelId = hotelId;
		}

		public Integer getRoomNumber() {
			return roomNumber;
		}

		public void setRoomNumber(Integer roomNumber) {
			this.roomNumber = roomNumber;
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
		
		
		

}
