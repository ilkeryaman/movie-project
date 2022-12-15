package com.eri.helper;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.model.Director;
import com.eri.model.Star;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MovieDataHelper extends DataHelper<com.eri.model.Movie> {

    void setMovies(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            movies = objectMapper.readValue(new File(pathName), new TypeReference<List<com.eri.model.Movie>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public com.eri.model.Movie createRandomMovie(int id){
        com.eri.model.Movie movie = new com.eri.model.Movie();
        movie.setId(id);
        movie.setTitle(TestData.MOVIE_NAME.getValue() + " " + id);
        movie.setCategories(Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName()));
        movie.setDirectors(Arrays.asList(new Director(TestData.DIRECTOR_NAME.getValue(), TestData.DIRECTOR_SURNAME.getValue())));
        movie.setStars(Arrays.asList(new Star(TestData.STAR_NAME.getValue(), TestData.STAR_SURNAME.getValue())));
        return movie;
    }
}
