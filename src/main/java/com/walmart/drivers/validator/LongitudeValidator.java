package com.walmart.drivers.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<LongitudeConstraint, Double>{

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return -180 <= value && value <= 180;
    }
    
}
