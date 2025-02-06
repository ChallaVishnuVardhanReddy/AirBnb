package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.BookingDto;
import com.example.AirBnb.Dto.BookingRequest;
import com.example.AirBnb.Dto.GuestDto;

import java.util.List;

public interface BookingService {


    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

}
