package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Booking;
import com.hotel.manager.entities.Payment;
import com.hotel.manager.enums.BookingStatus;
import com.hotel.manager.enums.PaymentStatus;
import com.hotel.manager.services.PaymentService;

@Component
public class PaymentFacade {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private BookingFacade bookingFacade;
	
	public List<Payment> getAllPayments() {
		return paymentService.findAll();
	}
	
	public Payment getPaymentById(Long paymentId) {
		return paymentService.findById(paymentId);
	}
	
	public Payment savePayment(Payment payment) {
		return paymentService.save(payment);
	}
	
	public Payment confirmPayment(Long paymentId) {
		Payment payment = this.getPaymentById(paymentId);
		payment.setPaid(PaymentStatus.PAID);
		Booking booking = payment.getBooking();
		booking.setBookingStatus(BookingStatus.CONFIRMED);
		bookingFacade.ConfirmPayment(booking);
		return payment;
	}
}
