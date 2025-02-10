package com.example.AirBnb.Strategy;

import com.example.AirBnb.Entities.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy{
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
