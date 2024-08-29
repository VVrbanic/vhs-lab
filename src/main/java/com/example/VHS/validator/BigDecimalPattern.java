package com.example.VHS.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = BigDecimalValidator.class)
@Documented
public @interface BigDecimalPattern {

    String message() default "Float can have zero or 2 decimals";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}