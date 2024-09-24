package com.hotel.manager.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientBookingsPageResource {
	@GetMapping(value = "/client_bookings")
	public String viewAllBookings() {
	        return "client_bookings.html";
	}
}
