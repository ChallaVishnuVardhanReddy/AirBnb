package com.example.AirBnb.Strategy;

import com.example.AirBnb.Entities.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface PricingStrategy {



    BigDecimal calculatePrice(Inventory inventory);
}
