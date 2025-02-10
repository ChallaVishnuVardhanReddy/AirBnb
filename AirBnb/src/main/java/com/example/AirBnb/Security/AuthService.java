package com.example.AirBnb.Security;

import com.example.AirBnb.Dto.LoginDto;
import com.example.AirBnb.Dto.SignUpRequestDto;
import com.example.AirBnb.Dto.UserDto;
import com.example.AirBnb.Entities.User;
import com.example.AirBnb.Entities.enums.Role;
import com.example.AirBnb.Exception.ResourceNotFoundException;
import com.example.AirBnb.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto){
          User user=userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
          if(user !=null)
          {
              throw new RuntimeException("User is already present with same emal id");
          }

          User newUser=modelMapper.map(signUpRequestDto,User.class);
           newUser.setRoles(Set.of(Role.GUEST));
           newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
          newUser=userRepository.save(newUser);
          return modelMapper.map(newUser,UserDto.class);
    }

    public String[] login(LoginDto loginDto){
         //check email and password are correct
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),loginDto.getPassword()
        ));
       User user=(User) authentication.getPrincipal();
       String arr[]=new String[2];
       arr[0]=jwtService.generateToken(user);
       arr[1]=jwtService.generateRefreshToken(user);
     return arr;
    }

    public String refreshToken(String refreshToken){
        Long id=jwtService.getUserIdFromToken(refreshToken);
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id:"+id));
        return jwtService.generateToken(user);
    }

}
