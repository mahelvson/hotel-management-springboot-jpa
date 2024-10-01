package com.hotel.manager.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotel.manager.dto.UserDTO;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Manager;
import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.exceptions.UserNotFoundException;
import com.hotel.manager.interfaces.ClientServiceInterface;
import com.hotel.manager.interfaces.ManagerServiceInterface;
import com.hotel.manager.interfaces.ReceptionistServiceInterface;
import com.hotel.manager.interfaces.UserServiceInterface;

@Component
public class UserFacade {
	
	@Autowired
	UserServiceInterface userService;
	
	@Autowired
	ClientServiceInterface clientService;
	
	@Autowired
	ReceptionistServiceInterface receptionistService;
	
	@Autowired
	ManagerServiceInterface managerService;
	
	
	public User createUser(UserDTO userDTO) {
		switch (userDTO.getUserType()) {
		case CLIENT:
			Client client = new Client(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getUserType(), userDTO.isLoyaltyMember()); 
			return clientService.createClient(client);
		case RECEPTIONIST:
			Receptionist receptionist = new Receptionist(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getUserType(), userDTO.isGrantDiscount());
			return receptionistService.createReceptionist(receptionist);
		case MANAGER:
			Manager manager = new Manager(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getUserType());
			return managerService.createManager(manager);
		default:
			throw new IllegalArgumentException("Invalid user type");
		}		
	}
	
	public User updateUser(Long userId, UserDTO newUserDTO) {
	    User user = userService.findById(userId);
	    
	    if (user instanceof Client) {
	        return updateClientDetails((Client) user, newUserDTO);
	    } else if (user instanceof Receptionist) {
	        return updateReceptionistDetails((Receptionist) user, newUserDTO);
	    } else if (user instanceof Manager) {
	        return updateManagerDetails((Manager) user, newUserDTO);
	    } else {
	        throw new IllegalArgumentException("Invalid user type");
	    }
	}

	private Client updateClientDetails(Client client, UserDTO newUserDTO) {
	    client.setEmail(newUserDTO.getEmail() == null ? client.getEmail() : newUserDTO.getEmail());
	    client.setName(newUserDTO.getName() == null ? client.getName() : newUserDTO.getName());
	    client.setPassword(newUserDTO.getPassword() == null ? client.getPassword() : newUserDTO.getPassword());
	    client.setIsLoyaltyMember(newUserDTO.isLoyaltyMember() ? newUserDTO.isLoyaltyMember(): client.getIsLoyaltyMember());
	    return clientService.updateClient(client);
	}

	private Receptionist updateReceptionistDetails(Receptionist receptionist, UserDTO newUserDTO) {
	    receptionist.setEmail(newUserDTO.getEmail() == null ? receptionist.getEmail() : newUserDTO.getEmail());
	    receptionist.setName(newUserDTO.getName() == null ? receptionist.getName() : newUserDTO.getName());
	    receptionist.setPassword(newUserDTO.getPassword() == null ? receptionist.getPassword() : newUserDTO.getPassword());
	    receptionist.setCanGrantDiscount(newUserDTO.isGrantDiscount());
	    return receptionistService.updateReceptionist(receptionist);
	}

	private Manager updateManagerDetails(Manager manager, UserDTO newUserDTO) {
	    manager.setEmail(newUserDTO.getEmail() == null ? manager.getEmail() : newUserDTO.getEmail());
	    manager.setName(newUserDTO.getName() == null ? manager.getName() : newUserDTO.getName());
	    manager.setPassword(newUserDTO.getPassword() == null ? manager.getPassword() : newUserDTO.getPassword());
	    return managerService.updateManager(manager);
	}
	
	public void deleteUser(Long userId) {
	    User user = userService.findById(userId);
	    
	    if (user != null) {
	    	userService.deleteUser(userId);
	    } else {
	    	throw new UserNotFoundException("User not found");
	    }
	}
	
	public List<User> findByUserType(UserType userType) {
		return userService.findByUserType(userType);
	}
	
	public User findUserById(Long id, UserType userType) {
		
		User user = userService.findById(id);
		
		if (user.getUserType() == userType) {
	        return user;
	    } else {
	        throw new UserNotFoundException("This id does not belong to a " + userType);
	    }
	}
	
	public User findUserByEmail(String email) {
		return userService.findByEmail(email);
	}
}
