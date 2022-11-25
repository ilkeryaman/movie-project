package com.eri.dal.service;

import com.eri.dal.entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    CategoryEntity saveCategory(CategoryEntity categoryEntity);

    List<CategoryEntity> getDefaultCategoryList();

    List<CategoryEntity> getCategoryList();

    CategoryEntity updateCategory(CategoryEntity categoryEntity, Long categoryId);
}
