package com.eri.dal.service.impl;

import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.repository.CategoryRepository;
import com.eri.dal.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public CategoryEntity saveCategory(CategoryEntity categoryEntity)
    {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public List<CategoryEntity> getDefaultCategoryList() {
        CategoryEntity action = new CategoryEntity();
        action.setName("action");
        CategoryEntity adventure = new CategoryEntity();
        adventure.setName("adventure");
        CategoryEntity crime = new CategoryEntity();
        crime.setName("crime");
        CategoryEntity drama = new CategoryEntity();
        drama.setName("drama");
        CategoryEntity fantasy = new CategoryEntity();
        fantasy.setName("fantasy");
        CategoryEntity sciFi = new CategoryEntity();
        sciFi.setName("sci-fi");
        return Arrays.asList(action, adventure, crime, drama, fantasy, sciFi);
    }

    @Override public List<CategoryEntity> getCategoryList()
    {
        return (List<CategoryEntity>) categoryRepository.findAll();
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity categoryEntity, Long categoryId)
    {
        CategoryEntity categoryAtDB = categoryRepository.findById(categoryId).get();

        if (Objects.nonNull(categoryEntity.getName()) && !"".equalsIgnoreCase(categoryEntity.getName())) {
            categoryAtDB.setName(categoryEntity.getName());
        }

        return categoryRepository.save(categoryAtDB);
    }
}
