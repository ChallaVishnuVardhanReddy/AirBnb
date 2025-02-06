package com.example.AirBnb.Dto;

import com.example.AirBnb.Entities.enums.Role;
import jakarta.persistence.*;

import java.util.Set;

public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private Set<Role> roles;


}
