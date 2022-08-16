package com.eri.validation;

import com.eri.validation.validator.CategoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({ FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CategoryValidator.class)
public @interface CategoryValidation {
    public String message() default "category must be one of action, adventure, crime, drama, fantasy, or sci-fi!";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}