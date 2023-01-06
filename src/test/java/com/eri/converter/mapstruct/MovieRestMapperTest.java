package com.eri.converter.mapstruct;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;

import java.math.BigInteger;
import java.util.Arrays;

public class MovieRestMapperTest {
    @Spy
    IMovieRestMapper movieRestMapper = Mappers.getMapper(IMovieRestMapper.class);

    //region generatedToModel
    @Test
    public void generatedToModelTest(){
        com.eri.swagger.movie_api.model.Movie movie = new com.eri.swagger.movie_api.model.Movie();
        movie.setId(1);
        movie.setTitle(TestData.MOVIE_NAME.getValue());
        movie.setCategories(Arrays.asList(Category.ACTION.getName()));
        movie.setDirectors(Arrays.asList(new com.eri.swagger.movie_api.model.Director().name(TestData.DIRECTOR_NAME.getValue()).surname(TestData.DIRECTOR_SURNAME.getValue())));
        movie.setStars(Arrays.asList(new com.eri.swagger.movie_api.model.Star().name(TestData.STAR_NAME.getValue()).surname(TestData.STAR_SURNAME.getValue())));
        // actual method call
        Movie movieMapped = movieRestMapper.generatedToModel(movie);
        // assertions
        Assert.assertEquals(movie.getId().intValue(), movieMapped.getId());
        Assert.assertEquals(movie.getTitle(), movieMapped.getTitle());
        Assert.assertEquals(movie.getCategories().size(), movieMapped.getCategories().size());
        Assert.assertEquals(movie.getCategories().get(0), movieMapped.getCategories().get(0));
        Assert.assertEquals(movie.getDirectors().size(), movieMapped.getDirectors().size());
        Assert.assertEquals(movie.getDirectors().get(0).getName(), movieMapped.getDirectors().get(0).getName());
        Assert.assertEquals(movie.getDirectors().get(0).getSurname(), movieMapped.getDirectors().get(0).getSurname());
        Assert.assertEquals(movie.getStars().size(), movieMapped.getStars().size());
        Assert.assertEquals(movie.getStars().get(0).getName(), movieMapped.getStars().get(0).getName());
        Assert.assertEquals(movie.getStars().get(0).getSurname(), movieMapped.getStars().get(0).getSurname());
    }

    @Test
    public void generatedToModelNullDataTest(){
        // actual method call
        Movie movieMapped = movieRestMapper.generatedToModel(null);
        // assertions
        Assert.assertNull(movieMapped);
    }

    @Test
    public void generatedToModelMissingDetailsTest(){
        com.eri.swagger.movie_api.model.Movie movie = new com.eri.swagger.movie_api.model.Movie();
        movie.setId(1);
        // actual method call
        Movie movieMapped = movieRestMapper.generatedToModel(movie);
        // assertions
        Assert.assertEquals(movie.getId().intValue(), movieMapped.getId());
    }
    //endregion generatedToModel

    //region modelToGenerated
    @Test
    public void modelToGeneratedTest(){
        Director director = new Director();
        director.setName(TestData.DIRECTOR_NAME.getValue());
        director.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        Star star = new Star();
        star.setName(TestData.STAR_NAME.getValue());
        star.setSurname(TestData.STAR_SURNAME.getValue());
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle(TestData.MOVIE_NAME.getValue());
        movie.setCategories(Arrays.asList(Category.ACTION.getName()));
        movie.setDirectors(Arrays.asList(director));
        movie.setStars(Arrays.asList(star));
        com.eri.swagger.movie_api.model.Movie movieMapped = movieRestMapper.modelToGenerated(movie);
        // assertions
        Assert.assertEquals(movie.getId(), movieMapped.getId().intValue());
        Assert.assertEquals(movie.getTitle(), movieMapped.getTitle());
        Assert.assertEquals(movie.getCategories().size(), movieMapped.getCategories().size());
        Assert.assertEquals(movie.getCategories().get(0), movieMapped.getCategories().get(0));
        Assert.assertEquals(movie.getDirectors().size(), movieMapped.getDirectors().size());
        Assert.assertEquals(movie.getDirectors().get(0).getName(), movieMapped.getDirectors().get(0).getName());
        Assert.assertEquals(movie.getDirectors().get(0).getSurname(), movieMapped.getDirectors().get(0).getSurname());
        Assert.assertEquals(movie.getStars().size(), movieMapped.getStars().size());
        Assert.assertEquals(movie.getStars().get(0).getName(), movieMapped.getStars().get(0).getName());
        Assert.assertEquals(movie.getStars().get(0).getSurname(), movieMapped.getStars().get(0).getSurname());
    }

    @Test
    public void modelToGeneratedNullDataTest(){
        com.eri.swagger.movie_api.model.Movie movieMapped = movieRestMapper.modelToGenerated(null);
        Assert.assertNull(movieMapped);
    }

    @Test
    public void modelToGeneratedMissingDetailsTest(){
        Movie movie = new Movie();
        movie.setId(1);
        // actual method call
        com.eri.swagger.movie_api.model.Movie movieMapped = movieRestMapper.modelToGenerated(movie);
        // assertions
        Assert.assertEquals(movie.getId(), movieMapped.getId().intValue());
    }
    //endregion modelToGenerated
}
