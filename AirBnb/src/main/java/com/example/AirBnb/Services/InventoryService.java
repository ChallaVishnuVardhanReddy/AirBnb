package com.example.AirBnb.Services;

import com.example.AirBnb.Entities.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);
    void deleteFutureInventory(Room room);
}
