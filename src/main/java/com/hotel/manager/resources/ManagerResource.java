package com.hotel.manager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.manager.entities.Manager;
import com.hotel.manager.services.ManagerService;

@RestController
@RequestMapping(value="/managers")
public class ManagerResource {
	
	@Autowired
	private ManagerService managerService;
	
	@GetMapping
	public ResponseEntity<List<Manager>> findAll() {
		List<Manager> manager = managerService.findAll();		
		return ResponseEntity.ok().body(manager);
	}
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<Manager> findById(@PathVariable Long id) {
		Manager manager = managerService.findById(id);
        return ResponseEntity.ok().body(manager);
    }
	@GetMapping(value = "/{email}")
    public ResponseEntity<Manager> findById(@PathVariable String email) {
		Manager manager = managerService.findByEmail(email);
        return ResponseEntity.ok().body(manager);
    }	
}
