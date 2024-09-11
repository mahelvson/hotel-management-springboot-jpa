package com.hotel.manager.reports;

import java.time.LocalDate;
import java.util.List;

import com.hotel.manager.enums.PaymentMethod;
import com.hotel.manager.enums.PaymentStatus;

public class BookingReport {

    private Long bookingId;
    private String clientName;
    private String clientEmail;
    private LocalDate dateCheckIn;
    private LocalDate dateCheckOut;
    private Double totalAmount;
    private List<Integer> roomNumbers;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;

    public BookingReport() {
    }

    public BookingReport(Long bookingId, String clientName, String clientEmail, LocalDate dateCheckIn,
    		LocalDate dateCheckOut, Double totalAmount, List<Integer> roomNumbers,
                         PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
        this.bookingId = bookingId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
        this.totalAmount = totalAmount;
        this.roomNumbers = roomNumbers;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

	
	// Getters e Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Integer> getRoomNumbers() {
        return roomNumbers;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
