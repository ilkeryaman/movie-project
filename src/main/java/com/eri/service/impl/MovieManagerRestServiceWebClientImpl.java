package com.eri.service.impl;

import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("movieManagerWebClientService")
public class MovieManagerRestServiceWebClientImpl implements IMovieManagerService {
    @Override
    public List<Movie> getMovies() {
        return null;
    }

    @Override
    public Movie findMovieById(int id) {
        return null;
    }

    @Override
    public void addMovie(Movie movie) {

    }

    @Override
    public void removeMovieById(int id) {

    }
}
