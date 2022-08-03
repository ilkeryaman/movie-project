package com.eri.service.memorydb.impl;

import com.eri.data.memorydb.MovieMemoryDB;
import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;
import com.eri.service.memorydb.IMovieMemoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieMemoryDBServiceImpl implements IMovieMemoryDBService {

    @Autowired
    MovieMemoryDB memoryDB;

    @PostConstruct
    public void afterInitialize(){
        addMovies();
    }

    private void addMovies(){
        addMovie(1, "Pulp Fiction",
                Arrays.asList(new Director("Quentin", "Tarantino")),
                Arrays.asList(new Star("John", "Travolta"), new Star("Uma", "Turman")),
                Arrays.asList("crime", "drama"));
        addMovie(2, "Fight Club",
                Arrays.asList(new Director("David", "Fincher")),
                Arrays.asList(new Star("Brad", "Pitt"), new Star("Edward", "Norton"), new Star("Meat", "Loaf")),
                Arrays.asList("drama"));
    }

    public List<Movie> getMovies(){
        return memoryDB.getMovies();
    }

    public void addMovie(int id, String title, List<Director> directorList, List<Star> starList, List<String> categories){
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDirectors(directorList);
        movie.setStars(starList);
        movie.setCategories(categories);
        getMovies().add(movie);
    }
}
