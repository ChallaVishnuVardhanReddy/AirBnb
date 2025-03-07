package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.RoomDto;
import com.example.AirBnb.Repositories.RoomRepository;
import com.example.AirBnb.Entities.Room;
import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);
    List<RoomDto> getAllRoomsInHotel(Long hotelId);
    RoomDto getRoomById(Long roomId);
    void deleteRoomById(Long roomId);

    RoomDto updateRoomById(Long hotelid, Long roomid, RoomDto roomDto);
}
