package com.example.AirBnb.Controller;


import com.example.AirBnb.Dto.HotelDto;
import com.example.AirBnb.Dto.HotelInfoDto;
import com.example.AirBnb.Dto.HotelPriceDto;
import com.example.AirBnb.Dto.HotelSearchRequest;
import com.example.AirBnb.Services.HotelService;
import com.example.AirBnb.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;
    @GetMapping("/search")
   public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        log.info("Attempting to search for hotel in {} city,from {} to {}",hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate());
        var page=inventoryService.searchHotels(hotelSearchRequest);
       return ResponseEntity.ok(page);
    }
    //TODO: can be paginated in future, just get additional page and size from pathvariable
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId)
    {
        log.info("Attempting to get the info of hotel with hotel id:{}",hotelId);
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}
