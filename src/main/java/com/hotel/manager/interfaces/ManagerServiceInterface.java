package com.hotel.manager.interfaces;

import java.util.List;

import com.hotel.manager.entities.Manager;

public interface ManagerServiceInterface {
	List<Manager> findAll();
	Manager findManagerById(Long managerId);
	Manager findManagerByEmail(String email);
	
	void deleteManager(Long managerId);
	Manager createManager(Manager manager);
	Manager updateManager(Manager manager);
}
