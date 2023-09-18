package com.samodule.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

/**
 * Implementation of {@link NotNullIfAnotherFieldHasValue} validator.
 **/
public class NotNullIfAnotherFieldHasValueValidator implements
		ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {

	private String fieldName;
	private String expectedFieldValue;
	private String dependFieldName;

	@Override
	public void initialize(NotNullIfAnotherFieldHasValue annotation) {
		fieldName = annotation.fieldName();
		expectedFieldValue = annotation.fieldValue();
		dependFieldName = annotation.dependFieldName();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		System.out.println("isValid  " + value);
		if (value == null) {
			return true;
		}
		String message = "";
		//			String fieldValue = BeanUtils.getProperty(value, fieldName);
		//			String dependFieldValue = BeanUtils.getProperty(value,dependFieldName);
					BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
					String fieldValue =  (String) wrapper.getPropertyValue(fieldName);
					String dependFieldValue = (String) wrapper.getPropertyValue(dependFieldName);
					
					// certificateName
					System.out.println("dependFieldName : " + dependFieldName
							+ " dependFieldValue : " + dependFieldValue);
					switch (dependFieldName) {
					case "fromDate":
						message = "Select from date ";
						break;
					case "toDate":
						message = "Select to date ";
						break;
					case "subjectTitle":
						message = "Enter main subject or subjects/ field of study";
						break;
					case "courseTitleOthers":
						message = "Enter other degree name";
						break;
					case "instituteOthers":
						message = "Enter institute other";
						break;
					case "universityOthers":
						message = "Enter university other";
						break;
					default:
						message = "Enter value for other";
					}
					if (expectedFieldValue.equals(fieldValue)
							&& dependFieldValue != null
							&& dependFieldValue.trim().isEmpty()) {
						ctx.disableDefaultConstraintViolation();					
						ctx.buildConstraintViolationWithTemplate(message)
								.addNode(dependFieldName).addConstraintViolation();
		
						return false;
					}

		return true;
	}

}
