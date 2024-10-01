package com.hotel.manager.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.manager.dto.UserDTO;
import com.hotel.manager.entities.Client;
import com.hotel.manager.entities.Manager;
import com.hotel.manager.entities.Receptionist;
import com.hotel.manager.entities.User;
import com.hotel.manager.enums.UserType;
import com.hotel.manager.exceptions.InvalidUserTypeException;
import com.hotel.manager.exceptions.UserNotFoundException;
import com.hotel.manager.facade.UserFacade;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserFacadeIntegrationTest {

    @Autowired
    private UserFacade userFacade;

    private UserDTO clientDTO;
    private UserDTO receptionistDTO;
    private UserDTO managerDTO;

    @BeforeEach
    public void setup() {
        clientDTO = new UserDTO(null, "Client Name", "client@test.com", "password", UserType.CLIENT, true, false);
        receptionistDTO = new UserDTO(null, "Receptionist Name", "receptionist@test.com", "password", UserType.RECEPTIONIST, false, true);
        managerDTO = new UserDTO(null, "Manager Name", "manager@test.com", "password", UserType.MANAGER, false, false);
    }

    @Test
    @Transactional
    public void testCreateClientSuccess() {
        User createdUser = userFacade.createUser(clientDTO);

        assertNotNull(createdUser);
        assertTrue(createdUser instanceof Client);
        assertEquals("Client Name", createdUser.getName());
        assertEquals(UserType.CLIENT, createdUser.getUserType());
    }

    @Test
    @Transactional
    public void testCreateReceptionistSuccess() {
        User createdUser = userFacade.createUser(receptionistDTO);

        assertNotNull(createdUser);
        assertTrue(createdUser instanceof Receptionist);
        assertEquals("Receptionist Name", createdUser.getName());
        assertEquals(UserType.RECEPTIONIST, createdUser.getUserType());
    }

    @Test
    @Transactional
    public void testCreateManagerSuccess() {
        User createdUser = userFacade.createUser(managerDTO);

        assertNotNull(createdUser);
        assertTrue(createdUser instanceof Manager);
        assertEquals("Manager Name", createdUser.getName());
        assertEquals(UserType.MANAGER, createdUser.getUserType());
    }

    @Test
    @Transactional
    public void testCreateUserInvalidUserType() {
        UserDTO invalidUserDTO = new UserDTO(null, "Invalid User", "invalid@test.com", "password", null, false, false);

        assertThrows(InvalidUserTypeException.class, () -> userFacade.createUser(invalidUserDTO));
    }

    @Test
    @Transactional
    public void testUpdateUserSuccess() {
        User createdUser = userFacade.createUser(clientDTO);
        UserDTO updateDTO = new UserDTO(createdUser.getId(), "Updated Client Name", "updated@test.com", "newpassword", UserType.CLIENT, true, false);
        User updatedUser = userFacade.updateUser(createdUser.getId(), updateDTO);
        assertNotNull(updatedUser);
        assertEquals("Updated Client Name", updatedUser.getName());
        assertEquals("updated@test.com", updatedUser.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue(passwordEncoder.matches("newpassword", updatedUser.getPassword()));
    }

    @Test
    @Transactional
    public void testUpdateUserNotFound() {
        UserDTO updateDTO = new UserDTO(999L, "Nonexistent User", "nonexistent@test.com", "password", UserType.CLIENT, false, false);

        assertThrows(UserNotFoundException.class, () -> userFacade.updateUser(999L, updateDTO));
    }

    @Test
    @Transactional
    public void testFindUserByIdSuccess() {
        User createdUser = userFacade.createUser(clientDTO);

        User foundUser = userFacade.findUserById(createdUser.getId(), UserType.CLIENT);

        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals(UserType.CLIENT, foundUser.getUserType());
    }

    @Test
    @Transactional
    public void testFindUserByIdNotFound() {
        assertThrows(UserNotFoundException.class, () -> userFacade.findUserById(999L, UserType.CLIENT));
    }

    @Test
    @Transactional
    public void testDeleteUserSuccess() {
        User createdUser = userFacade.createUser(clientDTO);
        assertNotNull(createdUser);

        userFacade.deleteUser(createdUser.getId());

        assertThrows(UserNotFoundException.class, () -> userFacade.findUserById(createdUser.getId(), UserType.CLIENT));
    }

    @Test
    @Transactional
    public void testFindUserByEmailSuccess() {
        User createdUser = userFacade.createUser(clientDTO);

        User foundUser = userFacade.findUserByEmail("client@test.com");

        assertNotNull(foundUser);
        assertEquals(createdUser.getEmail(), foundUser.getEmail());
    }

    @Test
    @Transactional
    public void testFindUserByEmailNotFound() {
        assertThrows(UserNotFoundException.class, () -> userFacade.findUserByEmail("nonexistent@test.com"));
    }
}
