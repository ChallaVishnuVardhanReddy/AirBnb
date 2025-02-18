package com.example.AirBnb.Repositories;

import com.example.AirBnb.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Optional<Booking>findByStripeSessionId(String sessionId);
}
