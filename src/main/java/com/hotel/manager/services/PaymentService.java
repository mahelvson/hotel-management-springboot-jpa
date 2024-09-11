package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Payment;
import com.hotel.manager.repositories.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

	public Payment findById(Long id) {
		Optional<Payment> obj = paymentRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Payment not found"));
	}

	public Payment save(Payment payment) {
		return paymentRepository.save(payment);
	}

	public void deleteById(Long id) {
		paymentRepository.deleteById(id);
	}
}
