package com.hotel.manager.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckBookingResource {
    
	@GetMapping(value="/prebooking")
	public String checkBooking() {
		return "booking.html";
	}
}