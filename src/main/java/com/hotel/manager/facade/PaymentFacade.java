package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Payment;
import com.hotel.manager.services.PaymentService;

@Component
public class PaymentFacade {
	
	@Autowired
	private PaymentService paymentService;
	
	
	public List<Payment> getAllPayments() {
		return paymentService.findAll();
	}
	
	public Payment getPaymentById(Long paymentId) {
		return paymentService.findById(paymentId);
	}
	
	public Payment savePayment(Payment payment) {
		return paymentService.save(payment);
	}
}
