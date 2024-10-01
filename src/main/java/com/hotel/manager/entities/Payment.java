package com.hotel.manager.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.manager.enums.PaymentMethod;
import com.hotel.manager.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_payment")
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double paymentValue;
	private PaymentMethod method;
	
	@OneToOne
	@JoinColumn(name="booking_id")
	@JsonIgnore
	private Booking booking;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	public Payment() {
		
	}
	public Payment(Double value, PaymentMethod method, Booking booking, PaymentStatus paymentStatus) {
		this.paymentValue = value;
		this.method = method;
		this.paymentStatus = paymentStatus;
	}
	
	public Long getId() {
		return id;
	}
	
	public Double getValue() {
		return paymentValue;
	}
	
	public void setValue(Double value) {
		this.paymentValue = value;
	}
	
	public PaymentMethod getMethod() {
		return method;
	}
	
	public void setMethod(PaymentMethod method) {
		this.method = method;
	}
	
	public Booking getBooking() {
		return booking;
	}
	
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	
	public void setPaid(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
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
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}	
}
