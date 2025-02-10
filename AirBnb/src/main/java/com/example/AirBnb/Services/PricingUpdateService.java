package com.example.AirBnb.Services;

import com.example.AirBnb.Entities.Hotel;
import com.example.AirBnb.Entities.HotelMinPrice;
import com.example.AirBnb.Entities.Inventory;
import com.example.AirBnb.Repositories.HotelMinPriceRepository;
import com.example.AirBnb.Repositories.HotelRepository;
import com.example.AirBnb.Repositories.InventoryRepository;
import com.example.AirBnb.Strategy.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {

     //Schedular to update the inventory and HotelMInPrice tables every hour

    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelRepository hotelRepository;
    private final PricingService pricingService;

    //@Shceduler(cron="sec min hours days month week")
    //Every hour at zero min,zero sec run this , for every day and mont and year
    //use cronhub to check the expression is right or wrong
    @Scheduled(cron="0 0 * * * *")
    public void updatePrices(){
         int page=0;
         //going to update 100 hotels at a time
         int batchSize=100;

         while(true){
             Page<Hotel> hotelPage=hotelRepository.findAll(PageRequest.of(page,batchSize));
             if(hotelPage.isEmpty()){
                 break;
             }
             hotelPage.getContent().forEach(this::updateHotelPrices);
             page++;
         }
    }
    private void updateHotelPrices(Hotel hotel){
        log.info("Updating hotel prices for hotel ID:{}",hotel.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate=LocalDate.now().plusYears(1);

        List<Inventory> inventoryList=inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endDate);

        updateInventoryPrices(inventoryList);//dynamic pricing

        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);
    }
   // Go through the inventory list from start date to end date and find out the min price and update the hotel's min price
   /* private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
          Map<LocalDate,BigDecimal> dailyMinPrices=inventoryList.stream()
                  .collect(Collectors.groupingBy(
                          Inventory::getDate,
                          Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                  ))
                  .entrySet().stream()
                  .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));
        //Prepare HotelPrice entities in bulk
        List<HotelMinPrice> hotelPrices=new ArrayList<>();
        dailyMinPrices.forEach((date,price)->{
            HotelMinPrice hotelPrice=hotelMinPriceRepository.findByHotelAndDate(hotel,date)
                    .orElse(new HotelMinPrice(hotel,date));
            hotelPrice.setPrice(price);
            hotelPrices.add(hotelPrice);
        });
        //save all
        hotelMinPriceRepository.saveAll(hotelPrices);
    }*/
   private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
       // Create a map to store the minimum price for each date
       Map<LocalDate, BigDecimal> dailyMinPrices = new HashMap<>();

       // Iterate through the inventory list to find the minimum price for each date
       for (Inventory inventory : inventoryList) {
           LocalDate date = inventory.getDate();
           BigDecimal price = inventory.getPrice();

           // Check if the date is already in the map and update with the minimum price
           if (dailyMinPrices.containsKey(date)) {
               BigDecimal existingMinPrice = dailyMinPrices.get(date);
               dailyMinPrices.put(date, price.min(existingMinPrice)); // Store the lowest price
           } else {
               dailyMinPrices.put(date, price);
           }
       }

       // Prepare a list to store HotelMinPrice entities
       List<HotelMinPrice> hotelPrices = new ArrayList<>();

       // Iterate through the dailyMinPrices map and update or create HotelMinPrice entries
       for (Map.Entry<LocalDate, BigDecimal> entry : dailyMinPrices.entrySet()) {
           LocalDate date = entry.getKey();
           BigDecimal price = entry.getValue();

           // Find an existing record or create a new one
           HotelMinPrice hotelPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                   .orElse(new HotelMinPrice(hotel, date));

           hotelPrice.setPrice(price);
           hotelPrices.add(hotelPrice);
       }

       // Save all hotel prices in bulk
       hotelMinPriceRepository.saveAll(hotelPrices);
   }


    private void updateInventoryPrices(List<Inventory> inventoryList) {
            inventoryList.forEach(inventory->{
                BigDecimal dynamicPrice=pricingService.calculateDynamicPricing(inventory);
                inventory.setPrice(dynamicPrice);
            });
            inventoryRepository.saveAll(inventoryList);
    }
}
