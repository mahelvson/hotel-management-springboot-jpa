package com.hotel.manager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.manager.entities.Manager;
import com.hotel.manager.exceptions.UserAlreadyExistisException;
import com.hotel.manager.exceptions.UserNotFoundException;
import com.hotel.manager.interfaces.ManagerServiceInterface;
import com.hotel.manager.repositories.ManagerRepository;

@Service
public class ManagerService implements ManagerServiceInterface {
	@Autowired
	private ManagerRepository managerRepository;

	@Override
	public Manager findManagerById(Long managerId) {
		return managerRepository.findManagerById(managerId);
	}

	@Override
	public Manager findManagerByEmail(String email) {
		return managerRepository.findByEmail(email);
	}

	@Override
	public void deleteManager(Long managerId) {
		managerRepository.deleteById(managerId);
	}

	@Override
	public Manager createManager(Manager manager) {
		Manager existingClient = this.findManagerByEmail(manager.getEmail());
		if (existingClient == null) {			
			return managerRepository.save(manager);
		} else {
			throw new UserAlreadyExistisException("Manager already exists");
		}
	}

	@Override
	public Manager updateManager(Manager manager) {
		Manager existingManager = this.findManagerByEmail(manager.getEmail());
		if (existingManager != null) {
			existingManager.setName(manager.getName());
			existingManager.setEmail(manager.getEmail());
			return managerRepository.save(manager);
		} else {
			throw new UserNotFoundException("Manager not found");
		}
	}

	@Override
	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

}
