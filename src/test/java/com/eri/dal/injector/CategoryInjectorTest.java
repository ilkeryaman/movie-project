package com.eri.dal.injector;

import com.eri.constant.enums.Category;
import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.service.ICategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class CategoryInjectorTest {
    @InjectMocks
    CategoryInjector categoryInjector;

    //region mocks
    @Mock
    ICategoryService categoryService;
    //endregion mocks

    //region injectCategories
    @Test
    public void injectCategoriesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(1L);
        categoryEntity1.setName(Category.ACTION.getName());
        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(2L);
        categoryEntity2.setName(Category.ADVENTURE.getName());
        // mocking
        Mockito.when(categoryService.saveCategory(Mockito.any(CategoryEntity.class))).thenReturn(null);
        Mockito.when(categoryService.getDefaultCategoryList()).thenReturn(Arrays.asList(categoryEntity1, categoryEntity2));
        // actual method call
        Method method = CategoryInjector.class.getDeclaredMethod("injectCategories");
        method.setAccessible(true);
        method.invoke(categoryInjector);
        // assertions
        Mockito.verify(categoryService, Mockito.times(2)).saveCategory(Mockito.any(CategoryEntity.class));
    }
    //endregion injectCategories
}
