package com.eri.dal.service.impl;

import com.eri.constants.enums.TestData;
import com.eri.dal.entity.DirectorEntity;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.entity.StarEntity;
import com.eri.dal.repository.MovieRepository;
import com.eri.exception.NullTitleException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieEntityDataHelper;
import com.eri.model.Movie;
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
import java.util.Objects;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {
    @InjectMocks
    MovieServiceImpl movieService;

    //region mocks
    @Mock
    MovieRepository movieRepositoryMock;
    //endregion mocks

    //region fields
    private MovieEntityDataHelper entityDataHelper;
    private List<MovieEntity> movieEntityList;
    //endregion fields


    @Before
    public void init(){
        entityDataHelper = new MovieEntityDataHelper();
        movieEntityList = entityDataHelper.getMovieList();
    }

    //region saveMovie
    @Test
    public void saveMovieTest(){
        MovieEntity movieEntity = movieEntityList.get(0);
        // mocking
        Mockito.when(movieRepositoryMock.save(Mockito.any(MovieEntity.class))).thenReturn(movieEntity);
        // actual method call
        MovieEntity movieEntityActual = movieService.saveMovie(movieEntity);
        // assertions
        Assert.assertEquals(movieEntity, movieEntityActual);
    }
    //endregion saveMovie

    //region getMovieList
    @Test
    public void getMovieListTest(){
        // mocking
        Mockito.when(movieRepositoryMock.findAll()).thenReturn(movieEntityList);
        // actual method call
        List<MovieEntity> movieEntityListActual =  movieService.getMovieList();
        // assertions
        Assert.assertTrue(movieEntityList.size() == movieEntityListActual.size());
        Assert.assertEquals(movieEntityList.get(0).getId(), movieEntityListActual.get(0).getId());
        Assert.assertEquals(movieEntityList.get(0).getTitle(), movieEntityListActual.get(0).getTitle());
        Assert.assertTrue(movieEntityList.get(0).getCategories().size() == movieEntityListActual.get(0).getCategories().size());
        movieEntityList.get(0).getCategories().forEach(
                categoryEntity ->
                        Assert.assertTrue(movieEntityListActual.get(0).getCategories().stream().anyMatch(
                                categoryEntityActual ->
                                        categoryEntityActual.getId() == categoryEntity.getId()
                                                && categoryEntityActual.getName().equals(categoryEntity.getName())
                        ))
        );
        Assert.assertTrue(movieEntityList.get(0).getDirectors().size() == movieEntityListActual.get(0).getDirectors().size());
        movieEntityList.get(0).getDirectors().forEach(
                directorEntity ->
                        Assert.assertTrue(movieEntityListActual.get(0).getDirectors().stream().anyMatch(
                                directorEntityActual ->
                                        directorEntityActual.getId() == directorEntity.getId()
                                                && directorEntityActual.getName().equals(directorEntity.getName())
                                                && directorEntityActual.getSurname().equals(directorEntity.getSurname())
                        ))
        );
        Assert.assertTrue(movieEntityList.get(0).getStars().size() == movieEntityListActual.get(0).getStars().size());
        movieEntityList.get(0).getStars().forEach(
                starEntity ->
                        Assert.assertTrue(movieEntityListActual.get(0).getStars().stream().anyMatch(
                                starEntityActual ->
                                        starEntityActual.getId() == starEntity.getId()
                                                && starEntityActual.getName().equals(starEntity.getName())
                                                && starEntityActual.getSurname().equals(starEntity.getSurname())
                        ))
        );
        Assert.assertEquals(movieEntityList.get(1).getId(), movieEntityListActual.get(1).getId());
        Assert.assertEquals(movieEntityList.get(1).getTitle(), movieEntityListActual.get(1).getTitle());
        Assert.assertTrue(movieEntityList.get(1).getCategories().size() == movieEntityListActual.get(1).getCategories().size());
        movieEntityList.get(1).getCategories().forEach(
                categoryEntity ->
                        Assert.assertTrue(movieEntityListActual.get(1).getCategories().stream().anyMatch(
                                categoryEntityActual ->
                                        categoryEntityActual.getId() == categoryEntity.getId()
                                                && categoryEntityActual.getName().equals(categoryEntity.getName())
                        ))
        );
        Assert.assertTrue(movieEntityList.get(1).getDirectors().size() == movieEntityListActual.get(1).getDirectors().size());
        movieEntityList.get(1).getDirectors().forEach(
                directorEntity ->
                        Assert.assertTrue(movieEntityListActual.get(1).getDirectors().stream().anyMatch(
                                directorEntityActual ->
                                        directorEntityActual.getId() == directorEntity.getId()
                                                && directorEntityActual.getName().equals(directorEntity.getName())
                                                && directorEntityActual.getSurname().equals(directorEntity.getSurname())
                        ))
        );
        Assert.assertTrue(movieEntityList.get(1).getStars().size() == movieEntityListActual.get(1).getStars().size());
        movieEntityList.get(1).getStars().forEach(
                starEntity ->
                        Assert.assertTrue(movieEntityListActual.get(1).getStars().stream().anyMatch(
                                starEntityActual ->
                                        starEntityActual.getId() == starEntity.getId()
                                                && starEntityActual.getName().equals(starEntity.getName())
                                                && starEntityActual.getSurname().equals(starEntity.getSurname())
                        ))
        );
    }
    //endregion getMovieList

    //region getMovieByIdTest
    @Test
    public void getMovieByIdTest(){
        long id = 1L;
        MovieEntity movieEntity = movieEntityList.get(0);
        // mocking
        Mockito.when(movieRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(movieEntity));
        // actual method call
        MovieEntity movieEntityActual = movieService.getMovieById(id);
        // assertions
        Assert.assertEquals(movieEntity, movieEntityActual);
    }

    @Test
    public void getMovieByIdMovieNotFoundTest(){
        // mocking
        Mockito.when(movieRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        // actual method call
        MovieEntity movieEntity = movieService.getMovieById(1200L);
        // assertions
        Assert.assertNull(movieEntity);
    }
    //endregion getMovieByIdTest

    //region updateMovie
    @Test
    public void updateMovieTest(){
        MovieEntity movieEntity = movieEntityList.get(0);
        // mocking
        Mockito.when(movieRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(movieEntity));
        Mockito.when(movieRepositoryMock.save(Mockito.any(MovieEntity.class))).thenReturn(movieEntity);
        // actual method call
        movieService.updateMovie(movieEntity, 1L);
        // assertions
        Mockito.verify(movieRepositoryMock, Mockito.times(1)).save(Mockito.any(MovieEntity.class));
    }

    @Test(expected = NullTitleException.class)
    public void updateMovieNullTitleTest(){
        MovieEntity movieEntity = movieEntityList.get(0);
        movieEntity.setTitle(null);
        // mocking
        Mockito.when(movieRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(movieEntity));
        // actual method call
        movieService.updateMovie(movieEntity, 1L);
    }
    //endregion updateMovie

    //region deleteMovie
    @Test
    public void deleteMovieTest(){
        Mockito.doNothing().when(movieRepositoryMock).deleteById(Mockito.anyLong());
        movieService.deleteMovie(1L);
    }
    //endregion deleteMovie
}
