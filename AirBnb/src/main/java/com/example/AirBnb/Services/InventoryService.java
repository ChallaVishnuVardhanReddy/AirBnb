package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.HotelDto;
import com.example.AirBnb.Dto.HotelSearchRequest;
import com.example.AirBnb.Entities.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
