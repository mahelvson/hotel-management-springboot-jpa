package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Payment;
import com.hotel.manager.facade.PaymentFacade;

@RestController
@RequestMapping(value="/payments")
public class PaymentResource {
	
	@Autowired
	private PaymentFacade paymentFacade;
	
	@GetMapping
	public ResponseEntity<List<Payment>> findAll() {
		List<Payment> payment = paymentFacade.getAllPayments();		
		return ResponseEntity.ok().body(payment);
	}
	
	@GetMapping(value = "/id/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment payment = paymentFacade.getPaymentById(id);
        return ResponseEntity.ok().body(payment);
    }
}
