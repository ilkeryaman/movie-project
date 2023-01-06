package com.eri.converter.mapstruct;

import com.eri.dal.entity.MovieEntity;
import com.eri.helper.MovieEntityDataHelper;
import com.eri.model.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieEntityToMovieMapperTest {
    @Spy
    MovieEntityToMovieMapper movieEntityToMovieMapper = Mappers.getMapper(MovieEntityToMovieMapper.class);

    //region fields
    MovieEntityDataHelper entityDataHelper;
    //endregion fields

    @Before
    public void init(){
        entityDataHelper = new MovieEntityDataHelper();
    }

    //region mapMovieEntityToMovie
    @Test
    public void mapMovieEntityToMovie(){
        MovieEntity movieEntity = entityDataHelper.getMovie();
        // actual method call
        Movie movie = movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntity);
        // assertions
        Assert.assertEquals(movieEntity.getId().intValue(), movie.getId());
        Assert.assertEquals(movieEntity.getTitle(), movie.getTitle());
        Assert.assertTrue(movieEntity.getCategories().size() == movie.getCategories().size());
        movieEntity.getCategories().forEach(categoryEntity ->
                Assert.assertTrue(movie.getCategories().stream().anyMatch(
                        category -> category.equals(categoryEntity.getName()))
                )
        );
        Assert.assertTrue(movieEntity.getDirectors().size() == movie.getDirectors().size());
        movieEntity.getDirectors().forEach(directorEntity ->
                Assert.assertTrue(movie.getDirectors().stream().anyMatch(
                        director -> director.getName().equals(directorEntity.getName())
                        && director.getSurname().equals(directorEntity.getSurname()))
                )
        );
        Assert.assertTrue(movieEntity.getStars().size() == movie.getStars().size());
        movieEntity.getStars().forEach(starEntity ->
                Assert.assertTrue(movie.getStars().stream().anyMatch(
                        star -> star.getName().equals(starEntity.getName())
                                && star.getSurname().equals(starEntity.getSurname()))
                )
        );
    }

    @Test
    public void mapMovieEntityToMovieNullDataTest(){
        // actual method call
        Movie movie = movieEntityToMovieMapper.mapMovieEntityToMovie(null);
        // assertions
        Assert.assertNull(movie);
    }

    @Test(expected = NullPointerException.class)
    public void mapMovieEntityToMovieMissingDetailsTest(){
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(1L);
        // actual method call
        movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntity);
    }
    //endregion mapMovieEntityToMovie
}
