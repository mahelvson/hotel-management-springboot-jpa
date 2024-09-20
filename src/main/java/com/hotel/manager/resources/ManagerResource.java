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

@RestController
@RequestMapping(value="/managers")
public class ManagerResource {
	
	@Autowired
	private ManagerFacade managerFacade;
	
	@GetMapping
	public ResponseEntity<List<Manager>> findAll() {
		List<Manager> manager = managerFacade.getAllManagers();		
		return ResponseEntity.ok().body(manager);
	}
	
	@GetMapping(value = "/id/{id}")
    public ResponseEntity<Manager> findById(@PathVariable Long id) {
		Manager manager = managerFacade.getManagerById(id);
        return ResponseEntity.ok().body(manager);
    }
	@GetMapping(value = "/email/{email}")
    public ResponseEntity<Manager> findByEmail(@PathVariable String email) {
		Manager manager = managerFacade.getManagerByEmail(email);
        return ResponseEntity.ok().body(manager);
    }
	
	@PostMapping
	public ResponseEntity<Manager> createManager(@RequestParam String name, @RequestParam String email,
			@RequestParam String password) {
		Manager manager = managerFacade.createManager(name, email, password);
		return ResponseEntity.ok(manager);
	}

	@DeleteMapping(value = "/deleteByEmail")
	public ResponseEntity<Void> deleteManagerByEmail(@RequestParam String email) {
		managerFacade.deleteManager(email);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/update/{id}")
	public ResponseEntity<Manager> updateManager(@PathVariable Long id, @RequestParam String name,
			@RequestParam String email, @RequestParam String password) {
		Manager updatedManager = managerFacade.updateManager(name, email, password);
		return ResponseEntity.ok(updatedManager);
	}
}
