package com.example.AirBnb.Controller;

import com.example.AirBnb.Dto.HotelDto;
import com.example.AirBnb.Services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;
    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto)
    {
        log.info("Attempting to create new hotel with name:"+hotelDto.getName());
        HotelDto resHotelDto=hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(resHotelDto, HttpStatus.CREATED);
    }
    @GetMapping({"/{hotelid}"})
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelid)
    {
        log.info("Attempting to get hotel details by id:"+hotelid);
        HotelDto resHotelDto=hotelService.getHotelById(hotelid);
        return ResponseEntity.ok(resHotelDto);
    }
    //get all hotels
    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels()
    {
        log.info("Attempting to get all hotels");
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId,@RequestBody HotelDto hotelDto)
    {
        log.info("Attempting to update hotel details by id:"+hotelId);
        HotelDto resHotelDto=hotelService.updateHotelById(hotelId,hotelDto);
        return ResponseEntity.ok(resHotelDto);
    }
    @DeleteMapping("/{hotelid}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelid)
    {
        log.info("Attempting to update hotel details by id:"+hotelid);
        hotelService.deleteHotelById(hotelid);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelid}")
    public ResponseEntity<Void> activateHotelById(@PathVariable Long hotelid)
    {
        log.info("Attempting to activate hotel with id:"+hotelid);
        hotelService.activateHotel(hotelid);
        return ResponseEntity.noContent().build();
    }



}
