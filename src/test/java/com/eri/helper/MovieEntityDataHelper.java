package com.eri.helper;

import com.eri.constant.enums.Category;
import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.entity.DirectorEntity;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.entity.StarEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MovieEntityDataHelper {
    public MovieEntity getExpectedMovieEntity(){
        Set<CategoryEntity> categoryEntitySet = new HashSet<>();
        Set<DirectorEntity> directorEntitySet = new HashSet<>();
        Set<StarEntity> starEntitySet = new HashSet<>();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName(Category.ACTION.getName());
        categoryEntitySet.add(categoryEntity);

        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(1L);
        directorEntity.setName("Name of director");
        directorEntity.setSurname("Surname of director");
        directorEntitySet.add(directorEntity);

        StarEntity starEntity = new StarEntity();
        starEntity.setId(1L);
        starEntity.setName("Name of star");
        starEntity.setSurname("Surname of star");
        starEntitySet.add(starEntity);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(110L);
        movieEntity.setTitle("The Test");
        movieEntity.setCategories(categoryEntitySet);
        movieEntity.setDirectors(directorEntitySet);
        movieEntity.setStars(starEntitySet);
        return movieEntity;
    }

    public List<MovieEntity> getExpectedMovieEntityList(){
        Set<CategoryEntity> categoryEntitySet = new HashSet<>();
        Set<DirectorEntity> directorEntitySet = new HashSet<>();
        Set<StarEntity> starEntitySet = new HashSet<>();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName(Category.ACTION.getName());
        categoryEntitySet.add(categoryEntity);

        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(1L);
        directorEntity.setName("Name of director");
        directorEntity.setSurname("Surname of director");
        directorEntitySet.add(directorEntity);

        StarEntity starEntity = new StarEntity();
        starEntity.setId(1L);
        starEntity.setName("Name of star");
        starEntity.setSurname("Surname of star");
        starEntitySet.add(starEntity);

        MovieEntity movieEntity1 = new MovieEntity();
        movieEntity1.setId(110L);
        movieEntity1.setTitle("The Test");
        movieEntity1.setCategories(categoryEntitySet);
        movieEntity1.setDirectors(directorEntitySet);
        movieEntity1.setStars(starEntitySet);
        return Arrays.asList(movieEntity1);
    }
}
