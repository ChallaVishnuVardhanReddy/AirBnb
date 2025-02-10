package com.example.AirBnb.Strategy;

import com.example.AirBnb.Entities.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);

        LocalDate today= LocalDate.now();
        LocalDate bookingDate=inventory.getDate();
        // Ensure bookingDate is valid
        if (bookingDate.isBefore(today)) {
            throw new IllegalArgumentException("Booking date cannot be in the past");
        }
        /*
        Long numberOfDays= ChronoUnit.DAYS.between(today,bookingDate)+1;
        if(numberOfDays<=7)
        {
            price=price.multiply(BigDecimal.valueOf(1.3));//or add some constant value,like rs500 extra.
        }*/
        if(bookingDate.isBefore(today.plusDays(7)))
        {
            price=price.multiply(BigDecimal.valueOf(1.15));//or add some constant value,like rs500 extra.

        }
        return price;
    }
}
