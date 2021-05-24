package com.walmart.drivers.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LongitudeValidator.class)
@Documented
public @interface LongitudeConstraint {

    String message() default "Longtitude range should be -180 to 180";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
