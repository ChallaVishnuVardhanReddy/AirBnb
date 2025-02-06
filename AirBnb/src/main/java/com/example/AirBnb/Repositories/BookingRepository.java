package com.example.AirBnb.Repositories;

import com.example.AirBnb.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
