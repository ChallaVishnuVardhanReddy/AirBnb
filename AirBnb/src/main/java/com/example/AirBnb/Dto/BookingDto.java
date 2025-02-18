package com.example.AirBnb.Dto;

import com.example.AirBnb.Entities.*;
import com.example.AirBnb.Entities.enums.BookingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long id;

//    private Long hotel_id;
//
//    private Long room_id;
//
//    private Long user_id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer roomsCount;

    private BookingStatus bookingStatus;

    private Set<GuestDto> guests;

    private BigDecimal amount;

}
