package com.hotel.manager.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationResource {
    
    @GetMapping("/registration")
    public String redirectToRegistration() {
        return "registration_visitor.html";
    }
}