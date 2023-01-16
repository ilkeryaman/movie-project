package com.eri.service;

import com.eri.model.Movie;

import java.util.List;

public interface IMovieManagerService {
    List<Movie> getMovies(boolean fromCache);
    List<Movie> listNewComers();
    Movie findMovieById(int id);
    void addMovie(Movie movie);
    void removeMovieById(int id);
}
