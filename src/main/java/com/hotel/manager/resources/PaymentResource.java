package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Payment;
import com.hotel.manager.services.PaymentService;

@RestController
@RequestMapping(value="/payments")
public class PaymentResource {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping
	public ResponseEntity<List<Payment>> findAll() {
		List<Payment> payment = paymentService.findAll();		
		return ResponseEntity.ok().body(payment);
	}
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment payment = paymentService.findById(id);
        return ResponseEntity.ok().body(payment);
    }
}
