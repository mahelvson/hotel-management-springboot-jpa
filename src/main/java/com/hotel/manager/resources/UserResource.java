package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.dto.UserDTO;
import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.facade.UserFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	UserFacade userFacade;

	@PostMapping
	@Tag(name = "User")
	@Operation(summary = "Register a new user", description = "Attempts to regisster a new user, passing a UserType enum", tags = {
			"user", "client", "manager", "receptionist", "crud" }, responses = {
					@ApiResponse(responseCode = "200", description = "User registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
		User user = userFacade.createUser(userDTO);
		return ResponseEntity.ok(user);
	}

	@PatchMapping(value = "/update/id/{userId}")
	@Tag(name = "User")
	@Operation(summary = "Update a user registration", description = "Attempt to update the register of a user.", tags = {
			"users, crud" }, responses = { @ApiResponse(responseCode = "200", description = "User info updated"),
					@ApiResponse(responseCode = "404", description = "User not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
		User updatedUser = userFacade.updateUser(userId, userDTO);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping(value = "/deleteById")
	@Tag(name = "User")
	@Operation(summary = "Remove a single user by its ID", description = "Delete a user from database", tags = {
			"hotel, crud" }, responses = { @ApiResponse(responseCode = "200", description = "Hotel removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<Void> deleteById(@RequestParam Long userId) {
		userFacade.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/clients")
	@Tag(name = "Client")
	@Operation(summary = "Search for all clients", description = "Return the list of all clients", tags = {
			"clients, search" }, responses = { @ApiResponse(responseCode = "200", description = "All clients listed"),
					@ApiResponse(responseCode = "404", description = "Clients not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<List<User>> findAllClients() {
		List<User> clients = userFacade.findByUserType(UserType.CLIENT);
		return ResponseEntity.ok().body(clients);
	}

	@GetMapping(value = "/clients/id/{id}")
	@Tag(name = "Client")
	@Operation(summary = "Search a client by ID", description = "Return a single client information passing its ID", tags = {
			"clients, search" }, responses = { @ApiResponse(responseCode = "200", description = "Client info listed"),
					@ApiResponse(responseCode = "404", description = "Client not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> findClientById(@PathVariable Long id) {
		User client = userFacade.findUserById(id, UserType.CLIENT);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/clients/email/{email}")
	@Tag(name = "Client")
	@Operation(summary = "Search client by E-mail (username)", description = "Return a single client passing its E-mail", tags = {
			"clients" }, responses = { @ApiResponse(responseCode = "200", description = "Client info listed"),
					@ApiResponse(responseCode = "404", description = "Client not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> findClientByEmail(@PathVariable String email) {
		User client = userFacade.findUserByEmail(email);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/receptionists")
	@Tag(name = "Receptionist")
	@Operation(summary = "Search for all receptionists", description = "Return the list of all receptionists", tags = {
			"receptionists, search" }, responses = {
					@ApiResponse(responseCode = "200", description = "All clients listed"),
					@ApiResponse(responseCode = "404", description = "Clients not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<List<User>> findAllReceptionists() {
		List<User> receptionists = userFacade.findByUserType(UserType.RECEPTIONIST);
		return ResponseEntity.ok().body(receptionists);
	}

	@GetMapping(value = "/receptionists/id/{id}")
	@Tag(name = "Receptionist")
	@Operation(summary = "Search a receptionists by ID", description = "Return a single receptionists information passing its ID", tags = {
			"receptionists, search" }, responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist info listed"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> findReceptionistById(@PathVariable Long id) {
		User client = userFacade.findUserById(id, UserType.RECEPTIONIST);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/receptionists/email/{email}")
	@Tag(name = "Receptionist")
	@Operation(summary = "Search receptionist by E-mail (username)", description = "Return a single receptionist passing its E-mail", tags = {
			"receptionists" }, responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist info listed"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> findReceptionistByEmail(@PathVariable String email) {
		User client = userFacade.findUserByEmail(email);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/managers")
	@Tag(name = "Managers")
	@Operation(summary = "Search for all managers", description = "Return the list of all managers", tags = {
			"receptionists", "search" }, responses = {
					@ApiResponse(responseCode = "200", description = "All managers listed"),
					@ApiResponse(responseCode = "404", description = "Managers not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<List<User>> findAllManagers() {
		List<User> receptionists = userFacade.findByUserType(UserType.MANAGER);
		return ResponseEntity.ok().body(receptionists);
	}

	@GetMapping(value = "/managers/id/{id}")
	@Tag(name = "Managers")
	@Operation(summary = "Search a managers by ID", description = "Return a single manager information passing its ID", tags = {
			"managers, search" }, responses = {
					@ApiResponse(responseCode = "200", description = "Managers info listed"),
					@ApiResponse(responseCode = "404", description = "Managers not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> findManagersById(@PathVariable Long id) {
		User client = userFacade.findUserById(id, UserType.MANAGER);
		return ResponseEntity.ok().body(client);
	}

	@GetMapping(value = "/managers/email/{email}")
	@Tag(name = "Managers")
	@Operation(summary = "Search receptionist by E-mail (username)", description = "Return a single receptionist passing its E-mail", tags = {
			"managers", "search" }, responses = {
					@ApiResponse(responseCode = "200", description = "Receptionist info listed"),
					@ApiResponse(responseCode = "404", description = "Receptionist not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"), })
	public ResponseEntity<User> findManagersByEmail(@PathVariable String email) {
		User client = userFacade.findUserByEmail(email);
		return ResponseEntity.ok().body(client);
	}

}