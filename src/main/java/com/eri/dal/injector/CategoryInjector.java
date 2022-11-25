package com.eri.dal.injector;

import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.service.ICategoryService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class CategoryInjector {
    @Resource
    ICategoryService categoryService;

    @PostConstruct
    private void injectCategories(){
        for (CategoryEntity categoryEntity : categoryService.getDefaultCategoryList()) {
            categoryService.saveCategory(categoryEntity);
        };
    }
}
