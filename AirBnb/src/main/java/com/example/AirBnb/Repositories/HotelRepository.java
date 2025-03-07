package com.example.AirBnb.Repositories;

import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findByOwner(User user);
}
