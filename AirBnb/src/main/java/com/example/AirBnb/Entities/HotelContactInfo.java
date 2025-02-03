package com.example.AirBnb.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class HotelContactInfo {
    private String address;
    private String location;
    private String email;
    private String phonenumber;
}
