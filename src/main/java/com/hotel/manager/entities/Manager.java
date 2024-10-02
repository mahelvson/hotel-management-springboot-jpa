package com.hotel.manager.entities;

import com.hotel.manager.enums.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_manager")
public class Manager extends User {
	// Classe que representa um objeto do tipo Gerente. Nao tem métodos além dos herdados, entretanto é preciso informar o id do gernete para criar recepcionistas.
	private static final long serialVersionUID = 1L;

	public Manager() {

	}

	public Manager(String nome, String email, String password, UserType userType) {
		super(nome, email, password, userType);
	}
}
