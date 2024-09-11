package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
