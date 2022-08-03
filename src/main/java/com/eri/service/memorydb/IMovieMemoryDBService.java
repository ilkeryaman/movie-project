package com.eri.service.memorydb;

import com.eri.model.Director;
import com.eri.model.Movie;
import com.eri.model.Star;

import java.util.List;

public interface IMovieMemoryDBService {
    List<Movie> getMovies();
    void addMovie(int id, String title, List<Director> directorList, List<Star> starList, List<String> categories);
}
