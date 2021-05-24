package com.walmart.drivers.services;

import com.walmart.drivers.models.Store;
import com.walmart.drivers.repositories.StoreRepository;

import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    public Store createStore(Store store){
        return storeRepository.save(store);
    }
}
