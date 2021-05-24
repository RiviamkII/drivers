package com.walmart.drivers.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<LatitudeConstraint, Double>{

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return -90 <= value && value <= 90;
    }
    
}
