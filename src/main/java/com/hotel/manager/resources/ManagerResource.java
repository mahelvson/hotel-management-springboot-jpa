package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Manager;
import com.hotel.manager.facade.ManagerFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value="/managers")
public class ManagerResource {
	
	@Autowired
	private ManagerFacade managerFacade;
	
	@GetMapping
	@Tag(name="Manager")
	@Operation(
			summary = "List for all managers",
			description = "Return the list of all managers",
			tags = {"manager, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "All managers listed"),
					@ApiResponse(responseCode = "404", description = "Manager not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<List<Manager>> findAll() {
		List<Manager> manager = managerFacade.getAllManagers();		
		return ResponseEntity.ok().body(manager);
	}
	
	@GetMapping(value = "/id/{id}")
	@Tag(name="Manager")
	@Operation(
			summary = "Search for manager by ID",
			description = "Return a single manager information passing its ID",
			tags = {"manager, search"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Manager info listed"),
					@ApiResponse(responseCode = "404", description = "Manager not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Manager> findById(@PathVariable Long id) {
		Manager manager = managerFacade.getManagerById(id);
        return ResponseEntity.ok().body(manager);
    }
	@GetMapping(value = "/email/{email}")
	@Tag(name="Manager")
	@Operation(
			summary = "Search manager by E-mail (username)",
			description = "Return a single manager information passing its E-mail",
			tags = {"manager"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Manager info listed"),
					@ApiResponse(responseCode = "404", description = "Manager not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
    public ResponseEntity<Manager> findByEmail(@PathVariable String email) {
		Manager manager = managerFacade.getManagerByEmail(email);
        return ResponseEntity.ok().body(manager);
    }
	
	@PostMapping
	@Tag(name="Manager")
	@Operation(
			summary = "Register a manager",
			description = "Attempt to register a new user of type {Manager} using request param (not request body as in client)",
			tags = {"manager, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Manager registered"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Manager> createManager(@RequestParam String name, @RequestParam String email,
			@RequestParam String password) {
		Manager manager = managerFacade.createManager(name, email, password);
		return ResponseEntity.ok(manager);
	}

	@DeleteMapping(value = "/deleteByEmail")
	@Tag(name="Manager")
	@Operation(
			summary = "Remove a single manager by its E-mail",
			description = "Delete a manager from database",
			tags = {"manager, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Manager removed"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Void> deleteManagerByEmail(@RequestParam String email) {
		managerFacade.deleteManager(email);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/update/{id}")
	@Tag(name="Manager")
	@Operation(
			summary = "Update a manager registration",
			description = "Attempt to update the information of a manager.",
			tags = {"manager, crud"},
			responses = {
					@ApiResponse(responseCode = "200", description = "Manager info updated"),
					@ApiResponse(responseCode = "404", description = "Manager not found"),
					@ApiResponse(responseCode = "500", description = "Internal error"),
			}
	)
	public ResponseEntity<Manager> updateManager(@PathVariable Long id, @RequestParam String name,
			@RequestParam String email, @RequestParam String password) {
		Manager updatedManager = managerFacade.updateManager(name, email, password);
		return ResponseEntity.ok(updatedManager);
	}
}
