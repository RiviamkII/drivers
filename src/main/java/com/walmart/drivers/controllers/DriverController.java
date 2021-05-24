package com.walmart.drivers.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.walmart.drivers.exception.InputParamException;
import com.walmart.drivers.models.Driver;
import com.walmart.drivers.services.DriverService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "driver")
@RestController
public class DriverController {
    private DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @GetMapping("/drivers")
    @ApiOperation(value = "fetch drivers near a store", response = Driver.class)
    public ResponseEntity<List<Driver>> fetchNearestDrivers(@RequestParam int amounts, 
                                                            @RequestParam String storeId){
        return Optional.ofNullable(driverService.fetchNearestDrivers(amounts, storeId))
                .filter(drivers -> !CollectionUtils.isEmpty(drivers)).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/driver")
    @ApiOperation(value = "send a driver to kafka", response = Driver.class)
    public ResponseEntity<Driver> sendToKafka(@RequestBody @Valid Driver driver, BindingResult bindingResult) throws InputParamException, JsonProcessingException{
        Optional<ObjectError> validationErr = bindingResult.getAllErrors().stream().findFirst();
        if(validationErr.isPresent()){
            throw new InputParamException(validationErr.get().getDefaultMessage());
        }
        driverService.sendToKafka(driver);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/driver/mysql")
    @ApiOperation(value = "create a driver", response = Driver.class)
    public ResponseEntity<Driver> createDriver(@RequestBody @Valid Driver driver, BindingResult bindingResult) throws InputParamException{
        Optional<ObjectError> validationErr = bindingResult.getAllErrors().stream().findFirst();
        if(validationErr.isPresent()){
            throw new InputParamException(validationErr.get().getDefaultMessage());
        }
        return Optional.ofNullable(driverService.createDriver(driver))
                .filter(d -> !Objects.isNull(d))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
