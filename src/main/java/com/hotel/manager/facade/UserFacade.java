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
import com.hotel.manager.exceptions.InvalidUserTypeException;
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
        if (userDTO == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

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
                throw new InvalidUserTypeException("Invalid user type: " + userDTO.getUserType());
        }
    }

    public User updateUser(Long userId, UserDTO newUserDTO) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        if (newUserDTO == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }
        
        User user = userService.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        if (user instanceof Client) {
            return updateClientDetails((Client) user, newUserDTO);
        } else if (user instanceof Receptionist) {
            return updateReceptionistDetails((Receptionist) user, newUserDTO);
        } else if (user instanceof Manager) {
            return updateManagerDetails((Manager) user, newUserDTO);
        } else {
            throw new InvalidUserTypeException("Invalid user type: " + user.getUserType());
        }
    }

    private Client updateClientDetails(Client client, UserDTO newUserDTO) {
        try {
            client.setEmail(newUserDTO.getEmail() == null ? client.getEmail() : newUserDTO.getEmail());
            client.setName(newUserDTO.getName() == null ? client.getName() : newUserDTO.getName());
            client.setPassword(newUserDTO.getPassword() == null ? client.getPassword() : newUserDTO.getPassword());
            client.setIsLoyaltyMember(newUserDTO.isLoyaltyMember() ? newUserDTO.isLoyaltyMember() : client.getIsLoyaltyMember());
            return clientService.updateClient(client);
        } catch (Exception e) {
            throw new RuntimeException("Error updating client details: " + e.getMessage());
        }
    }

    private Receptionist updateReceptionistDetails(Receptionist receptionist, UserDTO newUserDTO) {
        try {
            receptionist.setEmail(newUserDTO.getEmail() == null ? receptionist.getEmail() : newUserDTO.getEmail());
            receptionist.setName(newUserDTO.getName() == null ? receptionist.getName() : newUserDTO.getName());
            receptionist.setPassword(newUserDTO.getPassword() == null ? receptionist.getPassword() : newUserDTO.getPassword());
            receptionist.setCanGrantDiscount(newUserDTO.isGrantDiscount());
            return receptionistService.updateReceptionist(receptionist);
        } catch (Exception e) {
            throw new RuntimeException("Error updating receptionist details: " + e.getMessage());
        }
    }

    private Manager updateManagerDetails(Manager manager, UserDTO newUserDTO) {
        try {
            manager.setEmail(newUserDTO.getEmail() == null ? manager.getEmail() : newUserDTO.getEmail());
            manager.setName(newUserDTO.getName() == null ? manager.getName() : newUserDTO.getName());
            manager.setPassword(newUserDTO.getPassword() == null ? manager.getPassword() : newUserDTO.getPassword());
            return managerService.updateManager(manager);
        } catch (Exception e) {
            throw new RuntimeException("Error updating manager details: " + e.getMessage());
        }
    }

    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        User user = userService.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        userService.deleteUser(userId);
    }

    public List<User> findByUserType(UserType userType) {
        if (userType == null) {
            throw new IllegalArgumentException("User type cannot be null");
        }
        return userService.findByUserType(userType);
    }

    public User findUserById(Long id, UserType userType) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (userType == null) {
            throw new IllegalArgumentException("User type cannot be null");
        }

        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }

        if (user.getUserType() != userType) {
            throw new UserNotFoundException("User with ID " + id + " is not of type " + userType);
        }

        return user;
    }

    public User findUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        return user;
    }
}
