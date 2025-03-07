package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.BookingDto;
import com.example.AirBnb.Dto.BookingRequest;
import com.example.AirBnb.Dto.GuestDto;
import com.stripe.model.Event;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface BookingService {


    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId) throws AccessDeniedException;
}
