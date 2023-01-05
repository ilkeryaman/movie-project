package com.eri.dal.entity;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.model.Movie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class MovieEntityTest {
    //region getSetId
    @Test
    public void getSetIdTest(){
        Long id = 1L;
        MovieEntity movieEntity = new MovieEntity();
        // actual method call
        movieEntity.setId(id);
        // assertions
        Assert.assertEquals(id, movieEntity.getId());
    }
    //endregion getSetId

    //region getSetTitle
    @Test
    public void getSetTitleTest(){
        MovieEntity movieEntity = new MovieEntity();
        // actual method call
        movieEntity.setTitle(TestData.MOVIE_NAME.getValue());
        // assertions
        Assert.assertEquals(TestData.MOVIE_NAME.getValue(), movieEntity.getTitle());
    }
    //endregion getSetTitle

    //region getSetCategories
    @Test
    public void getSetCategoriesTest(){
        MovieEntity movieEntity = new MovieEntity();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName(Category.ACTION.getName());
        Set<CategoryEntity> categoryEntitySet = new HashSet<>(Arrays.asList(categoryEntity));
        // actual method call
        movieEntity.setCategories(categoryEntitySet);
        // assertions
        Assert.assertEquals(categoryEntitySet, movieEntity.getCategories());
    }
    //endregion getSetCategories

    //region getSetDirectors
    @Test
    public void getSetDirectorsTest(){
        MovieEntity movieEntity = new MovieEntity();
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(1L);
        directorEntity.setName(TestData.DIRECTOR_NAME.getValue());
        directorEntity.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        Set<DirectorEntity> directorEntitySet = new HashSet<>(Arrays.asList(directorEntity));
        // actual method call
        movieEntity.setDirectors(directorEntitySet);
        // assertions
        Assert.assertEquals(directorEntitySet, movieEntity.getDirectors());
    }
    //endregion getSetDirectors

    //region getSetStars
    @Test
    public void getSetStarsTest(){
        MovieEntity movieEntity = new MovieEntity();
        StarEntity starEntity = new StarEntity();
        starEntity.setId(1L);
        starEntity.setName(TestData.STAR_NAME.getValue());
        starEntity.setSurname(TestData.STAR_SURNAME.getValue());
        Set<StarEntity> starEntitySet = new HashSet<>(Arrays.asList(starEntity));
        // actual method call
        movieEntity.setStars(starEntitySet);
        // assertions
        Assert.assertEquals(starEntitySet, movieEntity.getStars());
    }
    //endregion getSetStars

    //region getSetUpdated
    @Test
    public void getSetUpdatedTest(){
        Date date = new Date();
        MovieEntity movieEntity = new MovieEntity();
        // actual method call
        movieEntity.setUpdated(date);
        // assertions
        Assert.assertEquals(date, movieEntity.getUpdated());
    }
    //endregion getSetUpdated

    //region isSetDeleted
    @Test
    public void isSetDeletedTest(){
        MovieEntity movieEntity = new MovieEntity();
        // actual method call
        movieEntity.setDeleted(true);
        // assertions
        Assert.assertTrue(movieEntity.isDeleted());
    }
    //endregion isSetDeleted

    //region onCreate
    @Test
    public void onCreateTest(){
        MovieEntity movieEntity = new MovieEntity();
        // actual method call
        movieEntity.onCreate();
        // assertions
        Assert.assertEquals(movieEntity.getCreated(), movieEntity.getUpdated());
    }
    //endregion onCreate

    //region onUpdate
    @Test
    public void onUpdateTest() throws InterruptedException {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.onCreate();
        Assert.assertEquals(movieEntity.getCreated(), movieEntity.getUpdated());
        Thread.sleep(1000);
        // actual method call
        movieEntity.onUpdate();
        // assertions
        Assert.assertNotEquals(movieEntity.getCreated(), movieEntity.getUpdated());
    }
    //endregion onUpdate

    //region equals
    @Test
    public void equalsTrueTest(){
        MovieEntity movieEntity1 = new MovieEntity();
        MovieEntity movieEntity2 = movieEntity1;
        // assertions
        Assert.assertTrue(movieEntity1.equals(movieEntity2));
    }

    @Test
    public void equalsFalseTest(){
        MovieEntity movieEntity = new MovieEntity();
        Movie movie = new Movie();
        // assertions
        Assert.assertFalse(movieEntity.equals(movie));
    }
    //endregion equals
}
