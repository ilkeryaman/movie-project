package com.eri.converter.mapstruct;

import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.service.ICategoryService;
import com.eri.model.Movie;
import org.mapstruct.*;

import javax.annotation.Resource;
import java.util.*;

@Mapper(componentModel = "spring")
public abstract class MovieToMovieEntityMapper {
    @Resource
    ICategoryService categoryService;

    @Mappings({
            @Mapping(target = "categories", source = "from", qualifiedByName = "setCategories")
    })
    public abstract MovieEntity mapMovieToMovieEntity(Movie from);

    @Named("setCategories")
    protected Set<CategoryEntity> setCategories(Movie from) {
        Set<CategoryEntity> categoryEntitySet = new HashSet<>();
        List<CategoryEntity> allCategories = categoryService.getCategoryList();
        for (String category : from.getCategories()) {
            Optional<CategoryEntity> categoryEntityOptional = allCategories.stream()
                    .filter(categoryEntity-> categoryEntity.getName().equals(category)).findFirst();
            if(categoryEntityOptional.isPresent()){
                categoryEntitySet.add(categoryEntityOptional.get());
            }

        }
        return categoryEntitySet;
    }
}
