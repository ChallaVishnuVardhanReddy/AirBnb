package com.example.AirBnb.Dto;


import com.example.AirBnb.Entities.Hotel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class RoomDto {

    private Long id;

    private String type;

    private BigDecimal basePrice;

    private String[] amenities;

    private String[] photos;

    private Integer totalCount;

    private Integer capacity;
}


