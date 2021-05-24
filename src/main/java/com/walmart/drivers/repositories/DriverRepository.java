package com.walmart.drivers.repositories;

import com.walmart.drivers.models.Driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String>{
    
}
