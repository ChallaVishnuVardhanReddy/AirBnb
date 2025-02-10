package com.example.AirBnb.Services;

import com.example.AirBnb.Entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserById(Long id);

}
