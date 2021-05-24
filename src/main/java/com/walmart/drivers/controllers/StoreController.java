package com.walmart.drivers.controllers;

import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import com.walmart.drivers.exception.InputParamException;
import com.walmart.drivers.models.Store;
import com.walmart.drivers.services.StoreService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "store")
@RestController
@RequestMapping("/store")
public class StoreController {
    
    private StoreService storeService;
    
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @PostMapping
    @ApiOperation(value = "create a store", response = Store.class)
    public ResponseEntity<Store> createDriver(@RequestBody @Valid Store store, BindingResult bindingResult) throws InputParamException{
        Optional<ObjectError> validationErr = bindingResult.getAllErrors().stream().findFirst();
        if(validationErr.isPresent()){
            throw new InputParamException(validationErr.get().getDefaultMessage());
        }
        return Optional.ofNullable(storeService.createStore(store))
                .filter(d -> !Objects.isNull(d))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
