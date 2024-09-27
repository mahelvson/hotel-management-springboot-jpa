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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value="/payments")
public class PaymentResource {
	
	@Autowired
	private PaymentFacade paymentFacade;
	
	@GetMapping
	@Tag(name="Payment")
	@Operation(
			summary = "Search for all payments",
			description = "Return the list of all payments",
			tags = {"payment"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All payments listed"),
					@ApiResponse(responseCode = "404", description = "payment not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Payment>> findAll() {
		List<Payment> payment = paymentFacade.getAllPayments();		
		return ResponseEntity.ok().body(payment);
	}
	
	@GetMapping(value = "/id/{id}")
	@Tag(name="Payment")
	@Operation(
			summary = "Search a payment by ID",
			description = "Return a single payment information passing its ID",
			tags = {"payment"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Payment info listed"),
					@ApiResponse(responseCode = "404", description = "Payment not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment payment = paymentFacade.getPaymentById(id);
        return ResponseEntity.ok().body(payment);
    }
}
