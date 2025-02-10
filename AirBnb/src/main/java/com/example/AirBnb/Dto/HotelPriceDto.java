package com.example.AirBnb.Dto;

import com.example.AirBnb.Entities.Hotel;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelPriceDto {
    private Hotel hotel;
    private Double price;
}
