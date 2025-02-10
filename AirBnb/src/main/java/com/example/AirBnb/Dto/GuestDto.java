package com.example.AirBnb.Dto;


import com.example.AirBnb.Entities.enums.Gender;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {
   private Long id;
   private UserDto user;
   private String name;
   private Gender gender;
   private Integer age;

}
