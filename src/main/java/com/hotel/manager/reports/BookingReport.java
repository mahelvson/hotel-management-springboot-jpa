package com.hotel.manager.reports;

import java.time.LocalDate;

import com.hotel.manager.enums.PaymentMethod;
import com.hotel.manager.enums.PaymentStatus;

public class BookingReport {
	// Representa um objeto do tipo Relatório, que concentra informações de reservas e dados de várias classes de negócio, envolvida na criação e confirmação de uma reserv.
    private Long id;
    private String clientName;
    private String clientEmail;
    private LocalDate dateCheckIn;
    private LocalDate dateCheckOut;
    private Double totalAmount;
    private Integer roomNumber;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;

    public BookingReport() {
    }

    public BookingReport(Long id, String clientName, String clientEmail, LocalDate dateCheckIn,
    		LocalDate dateCheckOut, Double totalAmount, Integer roomNumber,
                         PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
        this.id = id;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
        this.totalAmount = totalAmount;
        this.roomNumber = roomNumber;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

	
	// Getters e Setters
    public Long getBookingId() {
        return id;
    }

    public void setBookingId(Long id) {
        this.id = id;
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

    public Integer getRoomNumber() {
        return roomNumber;
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
