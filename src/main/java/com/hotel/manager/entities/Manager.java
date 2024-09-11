package com.hotel.manager.entities;

import com.hotel.manager.enums.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_manager")
public class Manager extends User {

	private static final long serialVersionUID = 1L;

	public Manager() {

	}

	public Manager(Long id, String nome, String email, String password, UserType userType) {
		super(id, nome, email, password, userType);
	}
}
