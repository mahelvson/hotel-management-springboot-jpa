package com.hotel.manager.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.User;
import com.hotel.manager.security.CustomUserDetails;
import com.hotel.manager.security.JwtUtil;
import com.hotel.manager.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
public class AuthResource {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@Tag(name="Login")
	@Operation(
			summary = "Login",
			description = "Login in the database",
			tags = {"login"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Login successfull"),
					@ApiResponse(responseCode = "401", description = "Bad credentials"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
		try {
			User user = userService.login(email, password);
			String token = jwtUtil.generateToken(user.getEmail(), user.getUserType());
			return ResponseEntity.ok(token);
		} catch (RuntimeException e) {
			return ResponseEntity.status(401).body("Invalid email or password");
		}
	}

	@GetMapping("/currentUser")
	@Tag(name="Login")
	@Operation(
			summary = "Get the current user",
			description = "Approach for specific actions on the plataform",
			tags = {"login"},
			responses = {
					@ApiResponse(responseCode = "200", description = "User logged in"),
					@ApiResponse(responseCode = "401", description = "User not logged logged"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        return ResponseEntity.ok(user);
    }


}
