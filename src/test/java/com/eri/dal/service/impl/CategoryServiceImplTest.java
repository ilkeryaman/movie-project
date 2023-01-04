package com.eri.dal.service.impl;

import com.eri.constant.enums.Category;
import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.repository.CategoryRepository;
import com.eri.exception.NullCategoryNameException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    @InjectMocks
    CategoryServiceImpl categoryService;

    //region mocks
    @Mock
    CategoryRepository categoryRepositoryMock;
    //endregion mocks

    //region saveCategory
    @Test
    public void saveCategoryTest(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName(Category.ACTION.getName());
        // mocking
        Mockito.when(categoryRepositoryMock.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);
        // actual method call
        CategoryEntity categoryEntityActual = categoryService.saveCategory(categoryEntity);
        // assertions
        Assert.assertEquals(categoryEntity, categoryEntityActual);
    }
    //endregion saveCategory

    //region getDefaultCategoryList
    @Test
    public void getDefaultCategoryListTest(){
        // actual method call
        List<CategoryEntity> categoryEntityList = categoryService.getDefaultCategoryList();
        // assertions
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.ACTION.getName())));
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.ADVENTURE.getName())));
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.CRIME.getName())));
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.DRAMA.getName())));
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.FANTASY.getName())));
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.SCI_FI.getName())));
    }
    //endregion getDefaultCategoryList

    //region getCategoryList
    @Test
    public void getCategoryListTest(){
        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(1L);
        categoryEntity1.setName(Category.ACTION.getName());
        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(2L);
        categoryEntity2.setName(Category.ADVENTURE.getName());
        // mocking
        Mockito.when(categoryRepositoryMock.findAll()).thenReturn(Arrays.asList(categoryEntity1, categoryEntity2));
        // actual method call
        List<CategoryEntity> categoryEntityList = categoryService.getCategoryList();
        // assertions
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.ACTION.getName())));
        Assert.assertTrue(categoryEntityList.stream().anyMatch(entity->entity.getName().equals(Category.ADVENTURE.getName())));
    }
    //endregion getCategoryList

    //region updateCategory
    @Test(expected = NullCategoryNameException.class)
    public void updateCategoryCategoryNameNullTest(){
        long id = 1L;
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        // mocking
        Mockito.when(categoryRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(categoryEntity));
        // actual method call
        categoryService.updateCategory(categoryEntity, id);
    }

    @Test
    public void updateCategoryTest(){
        long id = 1L;
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setName(Category.ACTION.getName());
        // mocking
        Mockito.when(categoryRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(categoryEntity));
        Mockito.when(categoryRepositoryMock.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);
        // actual method call
        categoryService.updateCategory(categoryEntity, id);
        // assertions
        Mockito.verify(categoryRepositoryMock, Mockito.times(1)).save(Mockito.any(CategoryEntity.class));
    }
    //endregion updateCategory
}
