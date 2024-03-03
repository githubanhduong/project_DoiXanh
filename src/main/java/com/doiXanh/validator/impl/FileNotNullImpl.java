package com.doiXanh.validator.impl;

import org.springframework.web.multipart.MultipartFile;

import com.doiXanh.validator.FileNotNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileNotNullImpl implements ConstraintValidator<FileNotNull, MultipartFile> {
    @Override
    public void initialize(FileNotNull constraintAnnotation) {
    }
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

		if (value.getOriginalFilename().isEmpty()) {
			return false;
		}
		return true;
	}

}
