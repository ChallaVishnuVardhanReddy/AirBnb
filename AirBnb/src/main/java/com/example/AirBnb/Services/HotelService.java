package com.example.AirBnb.Services;

import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Dto.HotelDto;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    List<HotelDto> getAllHotels();
    HotelDto updateHotelById(Long id,HotelDto hotelDto);
   void deleteHotelById(Long id);
}
