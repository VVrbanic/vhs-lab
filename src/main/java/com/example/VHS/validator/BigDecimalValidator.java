package com.example.VHS.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;


public class BigDecimalValidator implements ConstraintValidator<BigDecimalPattern, BigDecimal> {

    @Override
    public void initialize(final BigDecimalPattern constraintAnnotation) {

    }

    @Override
    public boolean isValid(final BigDecimal value,
                           final ConstraintValidatorContext context) {
        return hasZeroOrTwoDecimalPlaces(value);
    }

    public boolean hasZeroOrTwoDecimalPlaces(BigDecimal number){
        return (number.scale() == 2 || number.scale() == 0);
    }
}