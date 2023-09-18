package com.samodule.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Logical judgment of gender restriction
 */
public class SexConstraintValidator implements ConstraintValidator<Sex, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && (value.equals("A") | value.equals("I"));
	}

	@Override
	public void initialize(Sex constraintAnnotation) {
		// TODO Auto-generated method stub

	}
}