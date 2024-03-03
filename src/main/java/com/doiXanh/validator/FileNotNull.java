package com.doiXanh.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.doiXanh.validator.impl.FileNotNullImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileNotNullImpl.class}) 
public @interface FileNotNull {
	String message() default "Not empty file image";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
