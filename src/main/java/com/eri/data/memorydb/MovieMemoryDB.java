package com.eri.data.memorydb;

import com.eri.model.Movie;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MovieMemoryDB {
    private List<Movie> movies;

    public MovieMemoryDB(){
        movies = new ArrayList<Movie>();
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
