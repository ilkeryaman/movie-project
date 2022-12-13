package com.eri.helper;

import com.eri.constant.enums.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MovieRestDataHelper {
    @Resource
    ObjectMapper objectMapper;

    public com.eri.swagger.movie_api.model.Movie getExpectedMovie(){
        List<com.eri.swagger.movie_api.model.Director> directors = new ArrayList<>();
        List<com.eri.swagger.movie_api.model.Star> stars = new ArrayList<>();

        com.eri.swagger.movie_api.model.Director director = new com.eri.swagger.movie_api.model.Director();
        director.setName("Name of director");
        director.setSurname("Surname of director");
        directors.add(director);

        com.eri.swagger.movie_api.model.Star star = new com.eri.swagger.movie_api.model.Star();
        star.setName("Name of star");
        star.setSurname("Surname of star");
        stars.add(star);

        com.eri.swagger.movie_api.model.Movie movie = new com.eri.swagger.movie_api.model.Movie();
        movie.setId(110);
        movie.setTitle("The Test");
        movie.setCategories(Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName()));
        movie.setDirectors(directors);
        movie.setStars(stars);
        return movie;
    }

    public List<com.eri.swagger.movie_api.model.Movie> getExpectedMovieList(boolean fromCache){
        List<com.eri.swagger.movie_api.model.Movie> moviesExpected = null;
        String pathName = fromCache ? "src/test/resources/data/movies_from_cache.json" : "src/test/resources/data/movies.json";
        try {
            moviesExpected = objectMapper.readValue(new File(pathName), new TypeReference<List<com.eri.swagger.movie_api.model.Movie>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesExpected;
    }
}
