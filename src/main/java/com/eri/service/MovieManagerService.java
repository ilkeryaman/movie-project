package com.eri.service;

import com.eri.exception.MovieNotFoundException;
import com.eri.model.Movie;

import java.util.List;
import java.util.Optional;

public abstract class MovieManagerService implements IMovieManagerService {

    @Override
    public Movie findMovieById(int id) {
        Optional<Movie> movieOptional = getMovies().stream().filter(movie -> movie.getId() == id).findFirst();
        if(movieOptional.isPresent()){
            return movieOptional.get();
        } else {
            throw new MovieNotFoundException();
        }
    }

    @Override
    public void addMovie(Movie movie) {
        getMovies().add(movie);
    }

    @Override
    public void removeMovieById(int id) {
        getMovies().removeIf(movie -> movie.getId() == id);
    }

    public abstract List<Movie> getMovies();
}
