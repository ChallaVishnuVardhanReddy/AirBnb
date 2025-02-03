package com.example.AirBnb.Advice;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class ApiError {
   private String message;
   private HttpStatus status;
   private List<String> subErrors;

}
