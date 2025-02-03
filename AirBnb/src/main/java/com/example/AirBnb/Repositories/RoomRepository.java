package com.example.AirBnb.Repositories;

import com.example.AirBnb.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {

}
