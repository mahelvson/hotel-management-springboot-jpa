package com.hotel.manager.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = encoder.encode("suaSenha");
        
    }

    public String getPassword() {
    	return password;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
