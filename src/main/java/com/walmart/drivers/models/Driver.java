package com.walmart.drivers.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.walmart.drivers.validator.LatitudeConstraint;
import com.walmart.drivers.validator.LongitudeConstraint;

import lombok.Data;

@Entity
@Table(name = "driver")
@Data
public class Driver {
    @Id
    private String id;

    @Column(name = "latitude")
    @LatitudeConstraint
    private Double latitude;

    @Column(name = "longitude")
    @LongitudeConstraint
    private Double longitude;
}
