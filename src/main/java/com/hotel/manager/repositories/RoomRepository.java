package com.hotel.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.manager.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{

}
