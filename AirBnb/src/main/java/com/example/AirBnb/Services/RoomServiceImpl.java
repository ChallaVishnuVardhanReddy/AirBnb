package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.RoomDto;
import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Entities.Room;
import com.example.AirBnb.Entities.User;
import com.example.AirBnb.Exception.ResourceNotFoundException;
import com.example.AirBnb.Exception.UnAuthorisedException;
import com.example.AirBnb.Repositories.HotelRepository;
import com.example.AirBnb.Repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.AirBnb.Utils.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a new room in hotel with Id:{}",hotelId);
        Hotel hotelEntity = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no hotel with id: " +hotelId));
        Room room=modelMapper.map(roomDto,Room.class);

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotelEntity.getOwner())){
            throw new UnAuthorisedException("User doesn't own this hotel with id:"+hotelId);
        }
        room.setHotel(hotelEntity);
        // Save the room entity first
        room = roomRepository.save(room);
        //creating room's inventory if hotel is active
        if(hotelEntity.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room,RoomDto.class);

    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all the rooms with hotelId:"+hotelId);
        Hotel hotelEntity = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no hotel with id: " +hotelId));
          List<Room> rooms=hotelEntity.getRooms();
          return rooms.stream()
                  .map(roomEntity->modelMapper.map(roomEntity,RoomDto.class))
                  .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting room with roomId:"+roomId);
        Room RoomEntity=roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("There is no room with id:"+roomId));
        return modelMapper.map(RoomEntity,RoomDto.class);
    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {
       log.info("Deleting room with id:"+roomId);
        Room room=roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("There is no room with id:"+roomId));
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(room.getHotel().getOwner())){
            throw new UnAuthorisedException("User doesn't own this rooom with id:"+roomId);
        }
       //Deleting rooms in inventroy
        log.info("Deleting the inventory of the room");
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
        log.info("Deleted room with id:"+roomId);
    }

    @Override
    @Transactional
    public RoomDto updateRoomById(Long hotelid, Long roomid, RoomDto roomDto) {
        log.info("updating the room with Id:{}",roomid);
        Hotel hotel=hotelRepository
                .findById(hotelid)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id:"+hotelid));
        User user=getCurrentUser();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own this hotel with id:"+hotel);
        }
        Room room=roomRepository.findById(roomid).orElseThrow(()
                ->new ResourceNotFoundException("Room not found with id:"+roomid));
        modelMapper.map(roomDto,room);
        room.setId(roomid);
        //TODO:if price or inventory is updated, then update the inventory for this room
        room=roomRepository.save(room);
        return modelMapper.map(room,RoomDto.class);
    }
}
