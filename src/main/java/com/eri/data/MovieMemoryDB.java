package com.eri.data;

import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;
import com.eri.service.memorydb.MovieMemoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MovieMemoryDB {
    private List<Movie> movies;

    @Autowired
    MovieMemoryDBService movieMemoryDBService;

    public MovieMemoryDB(){
        movies = new ArrayList<Movie>();
    }

    @PostConstruct
    public void afterInitialize(){
        movieMemoryDBService.addMovie(movies, 1, "Pulp Fiction",
                Arrays.asList(new Director("Quentin", "Tarantino")),
                Arrays.asList(new Star("John", "Travolta"), new Star("Uma", "Turman")),
                Arrays.asList("crime", "drama"));
        movieMemoryDBService.addMovie(movies, 2, "Fight Club",
                Arrays.asList(new Director("David", "Fincher")),
                Arrays.asList(new Star("Brad", "Pitt"), new Star("Edward", "Norton"), new Star("Meat", "Loaf")),
                Arrays.asList("drama"));
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
