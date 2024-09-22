package com.hotel.manager.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.User;
import com.hotel.manager.security.JwtUtil;
import com.hotel.manager.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResource {

	
	@Autowired 
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.login(email, password);
            String token = jwtUtil.generateToken(user.getEmail(), user.getUserType());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
	
}
