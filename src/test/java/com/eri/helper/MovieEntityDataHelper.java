package com.eri.helper;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.entity.DirectorEntity;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.entity.StarEntity;

import java.util.*;

public class MovieEntityDataHelper extends DataHelper<com.eri.dal.entity.MovieEntity> {

    void setMovies(){
        movies = getMovieEntityList();
    }

    private List<com.eri.dal.entity.MovieEntity> getMovieEntityList(){
        Set<CategoryEntity> categoryEntitySet1 = new HashSet<>(Arrays.asList(
                getCategory(1L, Category.ACTION.getName()),
                getCategory(2L, Category.SCI_FI.getName())
        ));
        Set<CategoryEntity> categoryEntitySet2 = new HashSet<>(Arrays.asList(
                getCategory(1L, Category.CRIME.getName()),
                getCategory(2L, Category.DRAMA.getName()),
                getCategory(3L, Category.FANTASY.getName())
        ));
        Set<DirectorEntity> directorEntitySet = new HashSet<>(Arrays.asList(
                getDirector(1L, TestData.DIRECTOR_NAME.getValue(), TestData.DIRECTOR_SURNAME.getValue()),
                getDirector(2L, TestData.DIRECTOR_NAME.getValue(), TestData.DIRECTOR_SURNAME.getValue())
        ));
        Set<StarEntity> starEntitySet = new HashSet<>(Arrays.asList(
                getStar(1L, TestData.STAR_NAME.getValue(), TestData.STAR_SURNAME.getValue()),
                getStar(2L, TestData.STAR_NAME.getValue(), TestData.STAR_SURNAME.getValue())
        ));

        MovieEntity movieEntity1 = new MovieEntity();
        movieEntity1.setId(1L);
        movieEntity1.setTitle(TestData.MOVIE_NAME.getValue() + " 1");
        movieEntity1.setCategories(categoryEntitySet1);
        movieEntity1.setDirectors(directorEntitySet);
        movieEntity1.setStars(starEntitySet);

        MovieEntity movieEntity2 = new MovieEntity();
        movieEntity2.setId(2L);
        movieEntity2.setTitle(TestData.MOVIE_NAME.getValue() + " 2");
        movieEntity2.setCategories(categoryEntitySet2);
        movieEntity2.setDirectors(directorEntitySet);
        movieEntity2.setStars(starEntitySet);

        return Arrays.asList(movieEntity1, movieEntity2);
    }

    public MovieEntity createRandomMovieEntity(long id){
        Set<CategoryEntity> categoryEntitySet1 = new HashSet<>(Arrays.asList(
                getCategory(1L, Category.ACTION.getName()),
                getCategory(2L, Category.DRAMA.getName())
        ));
        Set<DirectorEntity> directorEntitySet = new HashSet<>(Arrays.asList(
                getDirector(1L, TestData.DIRECTOR_NAME.getValue(), TestData.DIRECTOR_SURNAME.getValue()),
                getDirector(2L, TestData.DIRECTOR_NAME.getValue(), TestData.DIRECTOR_SURNAME.getValue())
        ));
        Set<StarEntity> starEntitySet = new HashSet<>(Arrays.asList(
                getStar(1L, TestData.STAR_NAME.getValue(), TestData.STAR_SURNAME.getValue()),
                getStar(2L, TestData.STAR_NAME.getValue(), TestData.STAR_SURNAME.getValue())
        ));

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(id);
        movieEntity.setTitle(TestData.MOVIE_NAME.getValue() + " " + id);
        movieEntity.setCategories(categoryEntitySet1);
        movieEntity.setDirectors(directorEntitySet);
        movieEntity.setStars(starEntitySet);
        return movieEntity;
    }

    private CategoryEntity getCategory(long id, String name){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setName(name);
        return categoryEntity;
    }

    private DirectorEntity getDirector(long id, String name, String surname){
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(id);
        directorEntity.setName(name);
        directorEntity.setSurname(surname);
        return directorEntity;
    }

    private StarEntity getStar(long id, String name, String surname){
        StarEntity starEntity = new StarEntity();
        starEntity.setId(id);
        starEntity.setName(name);
        starEntity.setSurname(surname);
        return starEntity;
    }

}
