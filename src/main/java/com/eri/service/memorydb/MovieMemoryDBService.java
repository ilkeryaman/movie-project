package com.eri.service.memorydb;

import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMemoryDBService {
    public void addMovie(List<Movie> movies, int id, String title, List<Director> directorList, List<Star> starList, List<String> categories){
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDirectors(directorList);
        movie.setStars(starList);
        movie.setCategories(categories);
        movies.add(movie);
    }
}
