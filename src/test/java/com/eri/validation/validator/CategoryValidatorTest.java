package com.eri.validation.validator;

import com.eri.constant.enums.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategoryValidatorTest {
    @InjectMocks
    CategoryValidator categoryValidator;

    //region mocks
    @Mock
    ConstraintValidatorContext constraintValidatorContextMock;
    //endregion mocks

    @Test
    public void isValidTest(){
        String invalidCategory = "comedy";
        List<String> listThatContainsValidCategories = Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName());
        List<String> listThatContainsSomeInvalidCategories = Arrays.asList(Category.ACTION.getName(), invalidCategory);
        // assertions
        Assert.assertTrue(categoryValidator.isValid(listThatContainsValidCategories, constraintValidatorContextMock));
        Assert.assertFalse(categoryValidator.isValid(listThatContainsSomeInvalidCategories, constraintValidatorContextMock));
    }

    @Test(expected = NullPointerException.class)
    public void isValidNullCategoryTest(){
        // actual method call
        categoryValidator.isValid(null, constraintValidatorContextMock);
    }
}
