package com.example.AirBnb.Services;

import com.example.AirBnb.Entities.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
