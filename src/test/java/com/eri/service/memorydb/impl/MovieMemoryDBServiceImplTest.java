package com.eri.service.memorydb.impl;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.data.memorydb.MovieMemoryDB;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MovieMemoryDBServiceImplTest {

    @InjectMocks
    MovieMemoryDBServiceImpl movieMemoryDBService;

    //region mocks
    @Mock
    MovieMemoryDB movieMemoryDB;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    private List<Movie> movieList;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        movieList = dataHelper.getMovieList();
    }

    //region getMovies
    @Test
    public void getMoviesTest(){
        // mocking
        Mockito.when(movieMemoryDB.getMovies()).thenReturn(movieList);
        // actual method call
        movieMemoryDBService.getMovies();
    }
    //endregion getMovies

    //region addMovie
    @Test
    public void addMovieTest(){
        int initialMovieCount = movieList.size();
        // mocking
        Mockito.when(movieMemoryDB.getMovies()).thenReturn(movieList);
        // actual method call
        int id = 3;
        Director director = new Director();
        director.setName(TestData.DIRECTOR_NAME.getValue());
        director.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        Star star = new Star();
        star.setName(TestData.STAR_NAME.getValue());
        star.setSurname(TestData.STAR_SURNAME.getValue());
        movieMemoryDBService.addMovie(
                id,
                TestData.MOVIE_NAME.getValue() + " " + id,
                Arrays.asList(director),
                Arrays.asList(star),
                Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName())
        );
        Optional<Movie> movieOptional = movieList.stream().filter(movie -> movie.getId() == id).findFirst();
        // assertions
        Assert.assertTrue(movieOptional.isPresent());
        Assert.assertEquals(initialMovieCount + 1, movieList.size());
        Assert.assertEquals(movieList.get(2).getId(), movieOptional.get().getId());
        Assert.assertEquals(movieList.get(2).getTitle(), movieOptional.get().getTitle());
        Assert.assertEquals(movieList.get(2).getCategories().size(), movieOptional.get().getCategories().size());
        Assert.assertEquals(movieList.get(2).getCategories().get(0), movieOptional.get().getCategories().get(0));
        Assert.assertEquals(movieList.get(2).getCategories().get(1), movieOptional.get().getCategories().get(1));
        Assert.assertEquals(movieList.get(2).getDirectors().size(), movieOptional.get().getDirectors().size());
        Assert.assertEquals(movieList.get(2).getDirectors().get(0).getName(), movieOptional.get().getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(2).getDirectors().get(0).getSurname(), movieOptional.get().getDirectors().get(0).getSurname());
        Assert.assertEquals(movieList.get(2).getStars().size(), movieOptional.get().getStars().size());
        Assert.assertEquals(movieList.get(2).getStars().get(0).getName(), movieOptional.get().getStars().get(0).getName());
        Assert.assertEquals(movieList.get(2).getStars().get(0).getSurname(), movieOptional.get().getStars().get(0).getSurname());
    }
    //endregion addMovie
}
