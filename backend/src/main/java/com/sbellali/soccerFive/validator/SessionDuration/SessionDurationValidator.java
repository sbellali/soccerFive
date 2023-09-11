package com.sbellali.soccerFive.validator.SessionDuration;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SessionDurationValidator implements ConstraintValidator<ValidSessionDuration, Double> {

    private final List<Double> allowedValues = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value == null || allowedValues.contains(value);
    }

    public List<Double> getAllowedValues() {
        return this.allowedValues;
    }

}
