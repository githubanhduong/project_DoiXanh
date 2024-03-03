package com.doiXanh.validator.impl;

import org.springframework.web.multipart.MultipartFile;

import com.doiXanh.validator.ValidFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFileImpl implements ConstraintValidator<ValidFile, MultipartFile> {

	private String[] types;

	@Override
	public void initialize(ValidFile validFile) {
		this.types = validFile.type();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

		Boolean bl = false;

		if (value.getOriginalFilename().isEmpty()) {
			bl = true;
		}
		for (String type : types) {
			if (type.equals(value.getContentType())) {
				return true;
			}
		}

		return bl;
	}
}
