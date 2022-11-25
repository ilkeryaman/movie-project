package com.eri.converter.mapstruct;

import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.entity.MovieEntity;
import com.eri.model.Movie;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MovieEntityToMovieMapper {
    @Mappings({
            @Mapping(target = "categories", source = "from", qualifiedByName = "setCategories")
    })
    public abstract Movie mapMovieEntityToMovie(MovieEntity from);

    @Named(value = "setCategories")
    protected List<String> setCategories(MovieEntity from) {
        List<String> categoryList = new ArrayList<>();
        for (CategoryEntity categoryEntity : from.getCategories()) {
            categoryList.add(categoryEntity.getName());
        }
        return categoryList;
    }
}
