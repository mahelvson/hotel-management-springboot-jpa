package com.hotel.manager.resources;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckBookingResource {
    
	@GetMapping("/prebooking")
	public String checkBooking(Authentication authentication) {
	    if (authentication != null && authentication.isAuthenticated()) {
	        return "booking.html";
	    }
	    return "redirect:/login";
	}
}