package com.example.AirBnb.Controller;

import com.example.AirBnb.Dto.RoomDto;
import com.example.AirBnb.Services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelid}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {

    private final RoomService roomService;
    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@RequestBody RoomDto roomDto, @PathVariable Long hotelid)
    {
        log.info("Attempting to create a new room in the hotel with hotelid:"+hotelid);
        RoomDto res=roomService.createNewRoom(hotelid,roomDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms(@PathVariable Long hotelid)
    {
        log.info("Attempting to get all rooms in a hotel with id:"+hotelid);
        List<RoomDto> roomDto=roomService.getAllRoomsInHotel(hotelid);
        return new ResponseEntity<>(roomDto,HttpStatus.OK);
    }
    @GetMapping("/{roomid}")
    public ResponseEntity<RoomDto> getRoomWithId(@PathVariable Long roomid)
    {
        log.info("Attempting to get room with id:"+roomid);
        RoomDto roomDto=roomService.getRoomById(roomid);
        return new ResponseEntity<>(roomDto,HttpStatus.OK);
    }
    @DeleteMapping("/{roomid}")
    public ResponseEntity<Void> deleteRoomWithId(@PathVariable Long roomid)
    {
        log.info("Attempting to delete room with id:"+roomid);
        roomService.deleteRoomById(roomid);
        return ResponseEntity.noContent().build();
    }
}
