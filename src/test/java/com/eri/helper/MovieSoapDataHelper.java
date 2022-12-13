package com.eri.helper;

import com.eri.constant.enums.Category;
import com.eri.generated.movieapi.stub.ListMoviesResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Arrays;

@Component
public class MovieSoapDataHelper {

    public com.eri.generated.movieapi.stub.Movie getExpectedMovie(){
        com.eri.generated.movieapi.stub.Director director = new com.eri.generated.movieapi.stub.Director();
        director.setName("Name of director");
        director.setSurname("Surname of director");
        com.eri.generated.movieapi.stub.Star star = new com.eri.generated.movieapi.stub.Star();
        star.setName("Name of star");
        star.setSurname("Surname of star");

        com.eri.generated.movieapi.stub.Movie movie = new com.eri.generated.movieapi.stub.Movie();
        movie.setId(BigInteger.valueOf(110L));
        movie.setTitle("The Test");
        movie.getCategories().addAll(Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName()));
        movie.getDirectors().addAll(Arrays.asList(director));
        movie.getStars().addAll(Arrays.asList(star));
        return movie;
    }

    public ListMoviesResponse getExpectedMovieList(){
        ListMoviesResponse response = new ListMoviesResponse();
        response.getMovies().addAll(Arrays.asList(getAMovie(1), getAMovie(2)));
        return response;
    }

    private com.eri.generated.movieapi.stub.Movie getAMovie(int someId){
        com.eri.generated.movieapi.stub.Director director = new com.eri.generated.movieapi.stub.Director();
        director.setName("Name of director " + someId);
        director.setSurname("Surname of director " + someId);
        com.eri.generated.movieapi.stub.Star star = new com.eri.generated.movieapi.stub.Star();
        star.setName("Name of star " + star);
        star.setSurname("Surname of star " + star);

        com.eri.generated.movieapi.stub.Movie movie = new com.eri.generated.movieapi.stub.Movie();
        movie.setId(BigInteger.valueOf(someId));
        movie.setTitle("The Test");
        movie.getCategories().addAll(Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName()));
        movie.getDirectors().addAll(Arrays.asList(director));
        movie.getStars().addAll(Arrays.asList(star));
        return movie;
    }

}
