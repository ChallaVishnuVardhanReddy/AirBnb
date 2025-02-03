package com.example.AirBnb.Services;

import com.example.AirBnb.Dto.HotelDto;
import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Exception.ResourceNotFoundException;
import com.example.AirBnb.Repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{


    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}",hotelDto.getName());
        Hotel hotelEntity=modelMapper.map(hotelDto,Hotel.class);
        //Making hotel active to false, when the hotel is created it should be false
        hotelEntity.setActive(false);
        hotelEntity=hotelRepository.save(hotelEntity);
        log.info("Created a new hotel with Id: {}",hotelDto.getId());
        return modelMapper.map(hotelEntity,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with id: {}",id);
       Hotel hotelEntity= hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found with id:"+id));
        return modelMapper.map(hotelEntity,HotelDto.class);
    }

    @Override
    public List<HotelDto> getAllHotels(){
        log.info("Getting all hotels!");
        List<Hotel> hotels = hotelRepository.findAll();
        if (hotels.isEmpty()) {
            throw new ResourceNotFoundException("No Hotels Available");
        }
        return hotels.stream()
                .map(hotelEntity->modelMapper.map(hotelEntity,HotelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {

        Hotel hotelEntity=hotelRepository.findById(id).orElse(null);
        if(hotelEntity==null)
        {
            throw new ResourceNotFoundException("There is no hotel with id:"+id);
        }
        hotelDto.setId(id);
        hotelEntity=modelMapper.map(hotelDto,Hotel.class);
        log.info("Updating the hotel with id:"+id);
        hotelEntity =hotelRepository.save(hotelEntity);
        return modelMapper.map(hotelEntity,HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long id) {
        Hotel hotelEntity = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no hotel with id: " + id));
        log.info("Deleting the Hotel with id:"+id);
        hotelRepository.deleteById(id);
    }


}
