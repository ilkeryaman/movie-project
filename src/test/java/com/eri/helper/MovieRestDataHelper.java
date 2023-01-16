package com.eri.helper;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.swagger.movie_api.model.Director;
import com.eri.swagger.movie_api.model.Star;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MovieRestDataHelper extends DataHelper<com.eri.swagger.movie_api.model.Movie> {
    void setMovies(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            movies = objectMapper.readValue(new File(pathName), new TypeReference<List<com.eri.swagger.movie_api.model.Movie>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public com.eri.swagger.movie_api.model.Movie createRandomMovie(int id){
        com.eri.swagger.movie_api.model.Movie movie = new com.eri.swagger.movie_api.model.Movie();
        movie.setId(id);
        movie.setTitle(TestData.MOVIE_NAME.getValue() + " " + id);
        movie.setCategories(Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName()));
        movie.setDirectors(Arrays.asList(new Director().name(TestData.DIRECTOR_NAME.getValue()).surname(TestData.DIRECTOR_SURNAME.getValue())));
        movie.setStars(Arrays.asList(new Star().name(TestData.STAR_NAME.getValue()).surname(TestData.STAR_SURNAME.getValue())));
        return movie;
    }
}
