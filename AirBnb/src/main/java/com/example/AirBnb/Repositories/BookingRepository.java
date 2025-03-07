package com.example.AirBnb.Repositories;

import com.example.AirBnb.Entities.Booking;
import com.example.AirBnb.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Optional<Booking>findByStripeSessionId(String sessionId);

    List<Booking> findByHotel(Hotel hotel);
}
