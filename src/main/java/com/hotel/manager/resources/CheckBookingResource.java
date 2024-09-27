package com.hotel.manager.resources;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
public class CheckBookingResource {
    
	@GetMapping("/prebooking")
	@Operation(
			summary = "Confirm the information for booking",
			description = "Review the information, but only creates a interface to sign in user access",
			tags = {"booking, confirmation, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public String checkBooking(Authentication authentication) {
	    if (authentication != null && authentication.isAuthenticated()) {
	        return "booking.html";
	    }
	    return "redirect:/login";
	}
}