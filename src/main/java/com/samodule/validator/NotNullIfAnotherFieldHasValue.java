package com.samodule.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



/**
 * Validates that field {@code dependFieldName} is not null if
 * field {@code fieldName} has value {@code fieldValue}.
 **/
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Repeatable(NotNullIfAnotherFieldHasValue.List.class) // only with hibernate-validator >= 6.x
@Constraint(validatedBy = NotNullIfAnotherFieldHasValueValidator.class)
@Documented
public @interface NotNullIfAnotherFieldHasValue {

    String fieldName();
    String fieldValue();
    String dependFieldName();

    String message() default "Enter value for other ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNullIfAnotherFieldHasValue[] value();
    }

}
