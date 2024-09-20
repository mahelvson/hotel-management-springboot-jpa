package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.entities.Manager;
import com.hotel.manager.services.ManagerService;

@Component
public class ManagerFacade {
	
	@Autowired
	private ManagerService managerService;
	
	public List<Manager> getAllManagers() {
		List<Manager> managers = managerService.findAll();
		return managers;
	}
	
	public Manager getManagerById(Long id) {
		Manager manager = managerService.findById(id);
		return manager;
	}
	public Manager getManagerByEmail(String email) {
		Manager manager = managerService.findByEmail(email);
		return manager;
	}
	
	public Manager createManager(String name, String email, String password) {
		Manager manager = managerService.createManager(name, email, password);
		return manager;
	}
	
	public void deleteManager(String email) {
		managerService.deleteByEmail(email);
	}
	public Manager updateManager(String name, String email, String password) {
		Manager updatedManager = managerService.updateManager(name,  email,  password);
		return updatedManager;
	}

}
