package com.eri.dal.service.impl;

import com.eri.constant.enums.Category;
import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.repository.CategoryRepository;
import com.eri.dal.service.ICategoryService;
import com.eri.exception.NullCategoryNameException;
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
        action.setId(1L);
        action.setName(Category.ACTION.getName());
        CategoryEntity adventure = new CategoryEntity();
        action.setId(2L);
        adventure.setName(Category.ADVENTURE.getName());
        CategoryEntity crime = new CategoryEntity();
        action.setId(3L);
        crime.setName(Category.CRIME.getName());
        CategoryEntity drama = new CategoryEntity();
        action.setId(4L);
        drama.setName(Category.DRAMA.getName());
        CategoryEntity fantasy = new CategoryEntity();
        action.setId(5L);
        fantasy.setName(Category.FANTASY.getName());
        CategoryEntity sciFi = new CategoryEntity();
        action.setId(6L);
        sciFi.setName(Category.SCI_FI.getName());
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
        } else {
            throw new NullCategoryNameException();
        }

        return categoryRepository.save(categoryAtDB);
    }
}
