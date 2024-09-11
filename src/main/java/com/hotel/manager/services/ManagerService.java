package com.hotel.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Manager;
import com.hotel.manager.repositories.ManagerRepository;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;

	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	public Manager findById(Long id) {
		Optional<Manager> obj = managerRepository.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("Manager not found"));
	}

	public Manager findByEmail(String email) {
		Optional<Manager> obj = managerRepository.findByEmail(email);
		return obj.orElseThrow(() -> new RuntimeException("Manager e-mail not found"));
	}

	public Manager save(Manager manager) {
		return managerRepository.save(manager);
	}

	public void deleteById(Long id) {
		managerRepository.deleteById(id);
	}
}
