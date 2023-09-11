package com.sbellali.soccerFive.validator.SessionDuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SessionDurationValidator.class)
public @interface ValidSessionDuration {
    String message() default "Invalid duration value. Allowed values are 1.0, 1.5, 2, 2.5, 3";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
