package com.example.AirBnb.Dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class HotelSearchRequest {

    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;//how many rooms needed
   //default page no is zero and size is 10 records
    private Integer page=0;
    private Integer size=10;
}
