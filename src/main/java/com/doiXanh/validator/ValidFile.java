package com.doiXanh.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.doiXanh.validator.impl.ValidFileImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidFileImpl.class})
public @interface ValidFile {
	String message() default "Invalid image file";
	String[] type();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
