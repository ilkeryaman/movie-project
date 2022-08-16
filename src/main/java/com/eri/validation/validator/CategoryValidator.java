package com.eri.validation.validator;

import com.eri.constant.enums.Category;
import com.eri.validation.CategoryValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<CategoryValidation, List<String>>
{
    @Override
    public boolean isValid(List<String> categories, ConstraintValidatorContext context) {
        return categories.stream().allMatch(category ->
                        Arrays.stream(Category.values()).anyMatch(c -> c.getName().equals(category))
        );
    }
}
