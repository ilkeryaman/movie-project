package com.eri.helper;

import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;
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
public class MovieDataHelper {
    @Resource
    ObjectMapper objectMapper;

    public Movie getExpectedMovie(){
        List<Director> directors = new ArrayList<>();
        List<Star> stars = new ArrayList<>();

        directors.add(new Director("Name of director", "Surname of director"));
        stars.add(new Star("Name of star", "Surname of star"));

        Movie movie = new Movie();
        movie.setId(110);
        movie.setTitle("The Test");
        movie.setCategories(Arrays.asList("drama", "action"));
        movie.setDirectors(directors);
        movie.setStars(stars);
        return movie;
    }

    public List<Movie> getExpectedMovieList(boolean fromCache){
        List<Movie> moviesExpected = null;
        String pathName = fromCache ? "src/test/resources/data/movies_from_cache.json" : "src/test/resources/data/movies.json";
        try {
            moviesExpected = objectMapper.readValue(new File(pathName), new TypeReference<List<Movie>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesExpected;
    }
}
