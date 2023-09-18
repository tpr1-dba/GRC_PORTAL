package com.samodule.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *Gender constraints
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexConstraintValidator.class)
public @interface Sex {

 String message() default "Select status";

 Class<?>[] groups() default { };

 Class<? extends Payload>[] payload() default { };
}
