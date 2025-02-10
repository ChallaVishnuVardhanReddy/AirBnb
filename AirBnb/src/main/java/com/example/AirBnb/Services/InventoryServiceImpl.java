package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.HotelDto;
import com.example.AirBnb.Dto.HotelPriceDto;
import com.example.AirBnb.Dto.HotelSearchRequest;
import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Entities.Inventory;
import com.example.AirBnb.Entities.Room;
import com.example.AirBnb.Repositories.HotelMinPriceRepository;
import com.example.AirBnb.Repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    @Override
    public void initializeRoomForAYear(Room room) {

        log.info("Initializing room for a year, inside inventoryServiceImpl,roomId:"+room.getId());
        LocalDate today=LocalDate.now();
        LocalDate endDate=today.plusYears(1);
        for(;!today.isAfter(endDate);today=today.plusDays(1)){
            Inventory inventory=Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }

    }

    @Override
    public void deleteAllInventories(Room room) {
        log.info("Iniside InventoryServiceImpl,Deleteing all inventiories of particular room with roomid: {}"+room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Iniside InventoryServiceImpl,Searching hotels for {} city, from {} to {} ",hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate());
        Pageable pageable= PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
        long dateCount= ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+ 1;

        //business logic -90 days
        Page<HotelPriceDto> hotelPage=
                hotelMinPriceRepository.findHotelWithAvailableInventory(hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(),
                hotelSearchRequest.getEndDate(),
                hotelSearchRequest.getRoomsCount(),
                dateCount,
                pageable
        );

        return hotelPage;

    }
}
