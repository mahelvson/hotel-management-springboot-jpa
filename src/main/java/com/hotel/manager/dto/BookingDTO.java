package com.hotel.manager.dto;

import java.time.LocalDate;

public class BookingDTO {

    private Long clientId;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guestsNumber;

    public BookingDTO() {
    }

    public BookingDTO(Long clientId, Long roomId, LocalDate checkIn, LocalDate checkOut, Integer guestsNumber) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestsNumber = guestsNumber;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getGuestsNumber() {
        return guestsNumber;
    }

    public void setGuestsNumber(Integer guestsNumber) {
        this.guestsNumber = guestsNumber;
    }
}
