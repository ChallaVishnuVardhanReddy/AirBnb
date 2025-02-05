package com.example.AirBnb.Repositories;

import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Entities.Inventory;
import com.example.AirBnb.Entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    void deleteByRoom(Room room);
    //@param is for giving variable name.
    //we are using distinct is because we just need to know the hotel were room are available and the other
    // reason is pagination might not work properly if we are passing similar hotel id's.
    @Query("""
            SELECT DISTINCT i.hotel
            FROM Inventory i
            WHERE i.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = false
                AND (i.totalCount -i.bookedCount) >= :roomsCount
            GROUP By i.hotel, i.room
            HAVING COUNT(i.date) = :dateCount
            """)
    Page<Hotel> findHotelWithAvailableInventory(
      @Param("city") String city,
      @Param("startDate")LocalDate startDate,
      @Param("endDate")LocalDate endDate,
      @Param("roomsCount") Integer roomsCount,
      @Param("dateCount") Long dateCount,
      Pageable pageable
    );

}
