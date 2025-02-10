package com.example.AirBnb.Dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignUpRequestDto {
private String email;
private String password;
private String name;

}
