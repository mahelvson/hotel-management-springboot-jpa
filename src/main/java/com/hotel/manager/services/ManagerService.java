package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Manager;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.repositories.ManagerRepository;

import jakarta.transaction.Transactional;

@Service
public class ManagerService {
	@Autowired
	private ManagerRepository managerRepository;
	
	public List<Manager> findAll() {
		return managerRepository.findAll();
	}
	
	public Manager findById(Long id) {
		Optional<Manager> obj = managerRepository.findById(id);
		return obj.orElseThrow(()-> new RuntimeException("Manager not found"));
	}
	
	public Manager findByEmail(String email) {
		Optional<Manager> obj = managerRepository.findByEmail(email);
		return obj.orElseThrow(()-> new RuntimeException("Manager e-mail not found"));
	}
	
	public Manager save(Manager manager) {
		return managerRepository.save(manager);
	}
	
	public void deleteById(Long id) {
		managerRepository.deleteById(id);
	}
	
	public Manager createManager(String name, String email, String password) {
		Manager manager = managerRepository.findByEmail(email).orElse(new Manager(null, "Default Name", email, "12345", UserType.CLIENT ));
		manager.setName(name);
		manager.setPassword(password);
		manager.setUserType(UserType.MANAGER);
		return this.save(manager);
		
	}
	@Transactional
	public void deleteByEmail(String email) {
		Manager manager = managerRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Manager " + email + " not found!"));
		managerRepository.deleteByEmail(manager.getEmail());
	}
	@Transactional
	public Manager updateManager(String name, String email, String password) {
		Manager manager = managerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("Manager not found"));
		manager.setEmail(email);
		manager.setName(name);
		manager.setPassword(password);
		
		return this.save(manager);
	}

}
