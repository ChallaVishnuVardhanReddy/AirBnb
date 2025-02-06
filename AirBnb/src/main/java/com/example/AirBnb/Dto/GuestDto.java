package com.example.AirBnb.Dto;


import com.example.AirBnb.Entities.enums.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class GuestDto {
   private Long id;
   private UserDto user;
   private String name;
   private Gender gender;
   private Integer age;

}
