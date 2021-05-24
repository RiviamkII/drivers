package com.walmart.drivers.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.drivers.models.Driver;
import com.walmart.drivers.models.Store;
import com.walmart.drivers.repositories.DriverRepository;
import com.walmart.drivers.repositories.StoreRepository;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DriverService {
    public static final String TOPIC = "driver_location";
    public static final String GROUP_1 = "group1";

    private DriverRepository driverRepository;
    private StoreRepository storeRepository;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper ob;

    public DriverService(DriverRepository driverRepository,
                         KafkaTemplate<String, String> kafkaTemplate,
                         StoreRepository storeRepository,
                         ObjectMapper ob){
        this.driverRepository = driverRepository;
        this.storeRepository = storeRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.ob = ob;
    }

    public List<Driver> fetchNearestDrivers(int amounts, String storeId){
        log.info("fetching {} drivers nearest to {}", amounts, storeId);
        Optional<Store> store = storeRepository.findById(storeId);
        List<Driver> drivers = driverRepository.findAll();
        if(store.isPresent()){
            return drivers.stream()
                .sorted((d1, d2) -> calculateNearestDriver(d1, d2, store.get()))
                .limit(amounts)
                .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public Driver createDriver(Driver driver){
        return driverRepository.save(driver);
    }

    public void sendToKafka(Driver driver) throws JsonProcessingException{
        log.info("send driver: {}", driver);
        String content = ob.writeValueAsString(driver);
        kafkaTemplate.send(TOPIC, content);
    }

    @KafkaListener(topics = TOPIC, groupId = GROUP_1)
    public void listener(@Payload String driverJson) throws JsonMappingException, JsonProcessingException{
        log.info("consume driver: {}", driverJson);
        Driver driverNew = ob.readValue(driverJson, Driver.class);
        driverRepository.save(driverNew);
    }

    private int calculateNearestDriver(Driver driver1, Driver driver2, Store store){
        double distance1 = Math.pow(driver1.getLatitude() - store.getLatitude(), 2)
                            + Math.pow(driver1.getLongitude() - store.getLongitude(), 2);
        double distance2 = Math.pow(driver2.getLatitude() - store.getLatitude(), 2)
                            + Math.pow(driver2.getLongitude() - store.getLongitude(), 2);
        BigDecimal d1 = new BigDecimal(Double.toString(distance1));
        BigDecimal d2 = new BigDecimal(Double.toString(distance2));
        return d1.compareTo(d2);
    }
    
}
