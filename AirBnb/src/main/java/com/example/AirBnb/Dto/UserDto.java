package com.example.AirBnb.Dto;

import com.example.AirBnb.Entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private Set<Role> roles;


}
