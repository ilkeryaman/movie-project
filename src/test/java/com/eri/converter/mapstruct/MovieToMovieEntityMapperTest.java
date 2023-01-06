package com.eri.converter.mapstruct;

import com.eri.constant.enums.Category;
import com.eri.dal.entity.CategoryEntity;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.service.ICategoryService;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieToMovieEntityMapperTest {
    @InjectMocks
    MovieToMovieEntityMapper movieToMovieEntityMapper = Mockito.spy(Mappers.getMapper(MovieToMovieEntityMapper.class));

    @Mock
    ICategoryService categoryService;

    //region fields
    MovieDataHelper dataHelper;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
    }

    //region mapMovieEntityToMovie
    @Test
    public void mapMovieEntityToMovieTest(){
        Movie movie = dataHelper.getMovie();
        // mocking
        Mockito.when(categoryService.getCategoryList()).thenReturn(getCategoryEntityList());
        // actual method call
        MovieEntity movieEntity = movieToMovieEntityMapper.mapMovieToMovieEntity(movie);
        // assertions
        Assert.assertEquals(movie.getId(), movieEntity.getId().intValue());
        Assert.assertEquals(movie.getTitle(), movieEntity.getTitle());
        Assert.assertTrue(movie.getCategories().size() == movieEntity.getCategories().size());
        movie.getCategories().forEach(category ->
                Assert.assertTrue(movieEntity.getCategories().stream().anyMatch(
                        categoryEntity -> categoryEntity.getName().equals(category))
                )
        );
        Assert.assertTrue(movie.getDirectors().size() == movieEntity.getDirectors().size());
        movie.getDirectors().forEach(director ->
                Assert.assertTrue(movieEntity.getDirectors().stream().anyMatch(
                        directorEntity -> directorEntity.getName().equals(director.getName())
                                && directorEntity.getSurname().equals(director.getSurname()))
                )
        );
        Assert.assertTrue(movie.getStars().size() == movieEntity.getStars().size());
        movie.getStars().forEach(star ->
                Assert.assertTrue(movieEntity.getStars().stream().anyMatch(
                        starEntity -> starEntity.getName().equals(star.getName())
                                && starEntity.getSurname().equals(star.getSurname()))
                )
        );
    }

    @Test
    public void mapMovieEntityToMovieNullDataTest(){
        // actual method call
        MovieEntity movieEntity = movieToMovieEntityMapper.mapMovieToMovieEntity(null);
        // assertions
        Assert.assertNull(movieEntity);
    }

    @Test(expected = NullPointerException.class)
    public void mapMovieEntityToMovieMissingDetailsTest(){
        Movie movie = new Movie();
        movie.setId(1);
        // actual method call
        movieToMovieEntityMapper.mapMovieToMovieEntity(movie);
    }

    private List<CategoryEntity> getCategoryEntityList(){
        CategoryEntity action = new CategoryEntity();
        action.setId(1L);
        action.setName(Category.ACTION.getName());
        CategoryEntity adventure = new CategoryEntity();
        action.setId(2L);
        adventure.setName(Category.ADVENTURE.getName());
        CategoryEntity crime = new CategoryEntity();
        action.setId(3L);
        crime.setName(Category.CRIME.getName());
        CategoryEntity drama = new CategoryEntity();
        action.setId(4L);
        drama.setName(Category.DRAMA.getName());
        CategoryEntity fantasy = new CategoryEntity();
        action.setId(5L);
        fantasy.setName(Category.FANTASY.getName());
        CategoryEntity sciFi = new CategoryEntity();
        action.setId(6L);
        sciFi.setName(Category.SCI_FI.getName());
        return Arrays.asList(action, adventure, crime, drama, fantasy, sciFi);
    }
    //endregion mapMovieEntityToMovie
}
