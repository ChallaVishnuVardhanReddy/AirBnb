package com.example.AirBnb.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class HotelInfoDto {
    //Need to send hotel details and all its rooms
   private HotelDto hotel;
   private List<RoomDto> rooms;
}
