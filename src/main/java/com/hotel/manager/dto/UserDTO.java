package com.hotel.manager.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotel.manager.enums.UserType;

import jakarta.validation.constraints.Email;

public class UserDTO {
    private Long id;
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    private String password;
    private UserType userType;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    private boolean isLoyaltyMember;
    private boolean grantDiscount;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String password, UserType userType, boolean isLoyaltyMember, boolean grantDiscount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = encoder.encode(password);
        this.userType = userType;
        this.isLoyaltyMember = isLoyaltyMember;
        this.grantDiscount  = grantDiscount;
        
    }

    public boolean isGrantDiscount() {
		return grantDiscount;
	}

	public void setGrantDiscount(boolean grantDiscount) {
		this.grantDiscount = grantDiscount;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public boolean isLoyaltyMember() {
		return isLoyaltyMember;
	}

	public void setLoyaltyMember(boolean isLoyaltyMember) {
		this.isLoyaltyMember = isLoyaltyMember;
	}
    
}
